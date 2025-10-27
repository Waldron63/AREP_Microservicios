const API_BASE = "http://localhost:8080";
let currentUser = null;
let autoRefresh = false;
let autoInterval = null;
let usersCache = [];

function show(sectionId) {
    ["authSection", "registerSection", "mainApp"].forEach(id =>
        document.getElementById(id).classList.add("hidden")
    );
    document.getElementById(sectionId).classList.remove("hidden");
}

document.getElementById("btnShowRegister").onclick = () => show("registerSection");
document.getElementById("btnShowLogin").onclick = () => show("authSection");

document.getElementById("btnRegister").onclick = async () => {
    const username = document.getElementById("registerUsername").value.trim();
    const password = document.getElementById("registerPassword").value.trim();
    const msg = document.getElementById("registerMsg");

    if (!username || !password) {
        msg.textContent = "Please fill in all fields.";
        msg.style.color = "red";
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/api/users/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id: null, username, password })
        });

        if (res.ok) {
            msg.textContent = "User registered successfully! You can now log in.";
            msg.style.color = "green";
        } else {
            msg.textContent = "Error while registering user.";
            msg.style.color = "red";
        }
    } catch {
        msg.textContent = "Connection error with the server.";
        msg.style.color = "red";
    }
};

document.getElementById("btnLogin").onclick = async () => {
    const username = document.getElementById("loginUsername").value.trim();
    const password = document.getElementById("loginPassword").value.trim();
    const msg = document.getElementById("authMsg");

    if (!username || !password) {
        msg.textContent = "Please fill in all fields.";
        msg.style.color = "red";
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/api/users/all`);
        usersCache = await res.json();
        const found = usersCache.find(u => u.username === username && u.password === password);

        if (found) {
            currentUser = found;
            document.getElementById("currentUser").textContent = currentUser.username;
            show("mainApp");
            loadPosts();
        } else {
            msg.textContent = "Incorrect username or password.";
            msg.style.color = "red";
        }
    } catch {
        msg.textContent = "Could not connect to the server.";
        msg.style.color = "red";
    }
};

document.getElementById("btnLogout").onclick = () => {
    currentUser = null;
    show("authSection");
};

const postText = document.getElementById("postText");
const charCount = document.getElementById("charCount");
postText.addEventListener("input", () => {
    charCount.textContent = 140 - postText.value.length;
});

document.getElementById("btnPost").onclick = async () => {
    const message = postText.value.trim();
    const msg = document.getElementById("postMsg");

    if (!currentUser) {
        msg.textContent = "You must be logged in to post.";
        msg.style.color = "red";
        return;
    }
    if (!message) {
        msg.textContent = "Your post can't be empty.";
        msg.style.color = "red";
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/api/posts`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                id: null,
                message,
                likes: 0,
                userId: currentUser.id
            })
        });
        if (res.ok) {
            msg.textContent = "Successfully posted!";
            msg.style.color = "green";
            postText.value = "";
            charCount.textContent = 140;
            loadPosts();
        } else {
            msg.textContent = "Error while posting.";
            msg.style.color = "red";
        }
    } catch {
        msg.textContent = "⚠Server connection error.";
        msg.style.color = "red";
    }
};

async function loadPosts() {
    const streamDiv = document.getElementById("stream");
    streamDiv.innerHTML = "<p>Loading posts...</p>";

    try {
        const res = await fetch(`${API_BASE}/api/posts`);
        const posts = await res.json();

        if (!Array.isArray(posts) || posts.length === 0) {
            streamDiv.innerHTML = "<p>No posts yet.</p>";
            return;
        }

        if (!usersCache.length) {
            const uRes = await fetch(`${API_BASE}/api/users/all`);
            usersCache = await uRes.json();
        }

        streamDiv.innerHTML = posts
            .reverse()
            .map(p => {
                const user = usersCache.find(u => u.id === p.userId);
                const username = user ? user.username : "anon";
                return `
          <div class="post">
            <p><strong>@${username}</strong>: ${p.message ?? ""}</p>
            <small class="muted">🕒 just now</small>
            <br>
            <button class="like-btn" onclick="likePost(${p.id}, ${p.likes ?? 0})">❤️ ${p.likes ?? 0}</button>
          </div>
        `;
            })
            .join("");
    } catch {
        streamDiv.innerHTML = "<p>Error while loading stream.</p>";
    }
}

async function likePost(id) {
    try {
        const res = await fetch(`${API_BASE}/api/posts/${id}`);
        const post = await res.json();

        const updatedPost = {
            ...post,
            likes: (post.likes ?? 0) + 1
        };

        await fetch(`${API_BASE}/api/posts/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedPost)
        });

        loadPosts();
    } catch (err) {
        console.error("Error while liking post", err);
    }
}
window.likePost = likePost;

document.getElementById("btnRefresh").onclick = loadPosts;
document.getElementById("btnAuto").onclick = function () {
    autoRefresh = !autoRefresh;
    this.textContent = `Auto: ${autoRefresh ? "ON" : "OFF"}`;
    if (autoRefresh) {
        autoInterval = setInterval(loadPosts, 5000);
    } else {
        clearInterval(autoInterval);
    }
};

show("authSection");