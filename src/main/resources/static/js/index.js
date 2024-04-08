window.addEventListener("load", loadAndDisplayUsers);

const logoutBtn = document.getElementById("logoutBtn");
logoutBtn.addEventListener("click", handleLogout);

const newMeetingBtn = document.getElementById("newMeetingBtn");
newMeetingBtn.addEventListener("click", handleNewMeeting);

const joinMeetingBtn = document.getElementById("joinMeetingBtn");
joinMeetingBtn.addEventListener("click", handleJoinMeeting);

function loadAndDisplayUsers() {
    // check if user is connected
    const connectedUser = localStorage.getItem("connectedUser");
    if(!connectedUser) {
        window.location = "login.html";
        return;
    }

    const usersListElement = document.getElementById("userList");

    // clear any exising content in the usersListElement
    usersListElement.innerHTML = "Loading ...";

    // retrieve the usersList from Local Storage
    fetch('http://localhost:8080/api/v1/users')
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            console.log(data);
            displayUsers(data, usersListElement);
        });
}

function displayUsers(users, usersListElement) {
    usersListElement.innerHTML = "";

    // loop through the users and create list items to display each user
    users.forEach(user => {
        const item = document.createElement("li");
        item.innerHTML = `
            <div>
                <i class="fa fa-user-circle"></i>
                ${user.userName} <i class="user-email">(${user.email})</i>
            </div>
            <i class="fa fa-lightbulb-o ${user.status === "online" ? "online" : "offline"}"></i>
        `;
        usersListElement.appendChild(item);
    });
}

function handleLogout() {
    fetch('http://localhost:8080/api/v1/users/logout', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: localStorage.getItem('connectedUser')
    })
        .then((response) => {
            return response;
        })
        .then((data) => {
            localStorage.removeItem("connectedUser");
            window.location.href = "login.html";
        });
}

function handleNewMeeting() {
    const connectedUser = JSON.parse(localStorage.getItem("connectedUser"));

    const url = `videocall.html?userName=${connectedUser.userName}`;

    window.open(url, "_blank");
}

function handleJoinMeeting() {
    const roomId = document.getElementById("meetingName").value;
    const connectedUser = JSON.parse(localStorage.getItem("connectedUser"));

    const url = `videocall.html?roomID=${roomId}&userName=${connectedUser.userName}`;

    window.open(url, "_blank");
}