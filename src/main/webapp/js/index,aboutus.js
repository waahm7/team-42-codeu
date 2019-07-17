/*Redirects the user to Message Board Chat instead of homepage
and about us, if the user is logged in */

function RedirectIfLoggedIn(){
	fetch('/login-status')
      	.then((response) => {
        	return response.json();
      	})
      	.then((loginStatus) => {
        if (loginStatus.isLoggedIn) {
            window.location.href = "/user-page.html?user=" + loginStatus.username;
        }
      });
}