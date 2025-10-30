const API_BASE = "https://jtnkb1qozh.execute-api.us-east-1.amazonaws.com/api";
let currentUser = null;
let usersCache = [];
let streamsCache = [];
let currentStream = null;

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
        const res = await fetch(`${API_BASE}/users/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id: null, username, password })
        });

        msg.textContent = res.ok
            ? "User registered successfully! You can now log in."
            : "Error while registering user.";
        msg.style.color = res.ok ? "green" : "red";
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
        const res = await fetch(`${API_BASE}/users/all`);
        usersCache = await res.json();
        const found = usersCache.find(u => u.username === username && u.password === password);

        if (found) {
            currentUser = found;
            document.getElementById("currentUser").textContent = currentUser.username;
            show("mainApp");
            loadStreams();
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
    currentStream = null;
    show("authSection");
};

async function loadStreams() {
    const streamDiv = document.getElementById("stream");
    streamDiv.innerHTML = "<p>Loading threads...</p>";

    try {
        const res = await fetch(`${API_BASE}/streams`);
        streamsCache = await res.json();

        if (!streamsCache.length) {
            streamDiv.innerHTML = "<p>No threads yet. Create one below!</p>";
            return;
        }

        streamDiv.innerHTML = streamsCache
            .map(
                s => `
      <div class="post">
        <h3>${s.name}</h3>
        <p class="muted">${s.postIds?.length || 0} posts</p>
        <button class="secondary" onclick="openStream(${s.id})">Open Thread</button>
      </div>
    `
            )
            .join("");
    } catch {
        streamDiv.innerHTML = "<p>Error while loading threads.</p>";
    }
}

document.getElementById("btnCreateStream").onclick = async () => {
    const name = prompt("Enter a title for your new thread:");
    if (!name) return;

    try {
        const res = await fetch(`${API_BASE}/streams`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id: null, name, postIds: [] })
        });

        if (res.ok) {
            loadStreams();
        } else {
            alert("Error creating thread");
        }
    } catch {
        alert("Connection error while creating thread");
    }
};

window.openStream = async function (id) {
    currentStream = streamsCache.find(s => s.id === id);
    const streamDiv = document.getElementById("stream");
    streamDiv.innerHTML = `
    <h2>🧵 ${currentStream.name}</h2>
    <div id="postsList"></div>
    <br><button class="secondary" onclick="backToStreams()">← Back</button>
  `;
    loadPostsForStream();
};

function backToStreams() {
    currentStream = null;
    loadStreams();
}

async function loadPostsForStream() {
    const postsList = document.getElementById("postsList");
    postsList.innerHTML = "<p>Loading posts...</p>";

    if (!currentStream.postIds || currentStream.postIds.length === 0) {
        postsList.innerHTML = "<p>No posts in this thread yet.</p>";
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/posts`);
        const allPosts = await res.json();
        const posts = allPosts.filter(p => currentStream.postIds.includes(p.id));

        if (!usersCache.length) {
            const uRes = await fetch(`${API_BASE}/users/all`);
            usersCache = await uRes.json();
        }

        postsList.innerHTML = posts
            .reverse()
            .map(p => {
                const username = usersCache.find(u => u.id === p.userId)?.username ?? "anon";
                const message = p.message ?? "(empty)";
                return `
          <div class="post">
            <p><strong>@${username}</strong>: ${message}</p>
            <small class="muted">❤️ ${p.likes ?? 0}</small>
            <br>
            <button class="like-btn" onclick="likePost(${p.id})">❤️ Like</button>
          </div>
        `;
            })
            .join("");
    } catch {
        postsList.innerHTML = "<p>Error loading posts.</p>";
    }
}

window.likePost = async function (id) {
    try {
        const res = await fetch(`${API_BASE}/posts/${id}`);
        const post = await res.json();
        const updated = { ...post, likes: (post.likes ?? 0) + 1 };

        await fetch(`${API_BASE}/posts/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updated)
        });
        loadPostsForStream();
    } catch (err) {
        console.error(err);
    }
};

document.getElementById("btnPost").onclick = async () => {
    const message = document.getElementById("postText").value.trim();
    const msg = document.getElementById("postMsg");

    if (!currentUser) {
        msg.textContent = "You must be logged in.";
        msg.style.color = "red";
        return;
    }
    if (!currentStream) {
        msg.textContent = "Select or create a thread first.";
        msg.style.color = "red";
        return;
    }
    if (!message) {
        msg.textContent = "Your post can't be empty.";
        msg.style.color = "red";
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/posts`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                id: null,
                message,
                likes: 0,
                userId: currentUser.id
            })
        });

        if (!res.ok) throw new Error("Post failed");
        const newPost = await res.json();

        const streamPostRes = await fetch(`${API_BASE}/streams/add`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                postId: newPost.id,
                streamId: currentStream.id
            })
        });

        if (!streamPostRes.ok) throw new Error("Failed to add post to thread");

        currentStream = await (await fetch(`${API_BASE}/streams/${currentStream.id}`)).json();

        document.getElementById("postText").value = "";
        msg.textContent = "Posted!";
        msg.style.color = "green";
        loadPostsForStream();
    } catch (err) {
        console.error(err);
        msg.textContent = "Error posting.";
        msg.style.color = "red";
    }
};

document.getElementById("btnRefresh").onclick = async () => {
    try {
        if (currentStream) {
            currentStream = await (await fetch(`${API_BASE}/streams/${currentStream.id}`)).json();
            loadPostsForStream();
        } else {
            loadStreams();
        }
    } catch (err) {
        console.error(err);
        alert("Error refreshing data.");
    }
};

const postText = document.getElementById("postText");
const charCount = document.getElementById("charCount");

postText.addEventListener("input", () => {
    const remaining = 140 - postText.value.length;
    charCount.textContent = remaining >= 0 ? remaining : 0;
});


show("authSection");