/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Get ?user=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const parameterUsername = urlParams.get('user');
const parameterSearch = urlParams.get('content');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
  document.getElementById('page-title').innerText = "Welcome \xa0\xa0" +parameterUsername + " !";
  document.title = parameterUsername + ' - Chat';
}

/**
 * Shows the message form if the user is logged in and viewing their own page.
 */
function showMessageFormIfViewingSelf() {
  fetch('/login-status')
      .then((response) => {
        return response.json();
      })
      .then((loginStatus) => {
        if (loginStatus.isLoggedIn &&
            loginStatus.username == parameterUsername) {
          const messageForm = document.getElementById('message-form');
          if (messageForm) messageForm.classList.remove('hidden');
        }
      });
	//document.getElementById('about-me-form').classList.remove('hidden');
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}

/** Fetches messages and add them to the page. */
function fetchMessages(button) {
	
	console.log(urlParams.toString());
	console.log(parameterSearch);
	console.log(parameterUsername);
var url;
    console.log("there:"+button);
   if(button===undefined){
     //url = '/messages?user=' + parameterUsername;
	 if (urlParams.has('content')){
		url='/messages?user='+parameterUsername+'&content='+parameterSearch;
	 }
	 else {
		 url='/messages?user='+parameterUsername;
	 }
  }
  else {

     button = encodeURIComponent(button);
     url='/messages?user='+parameterUsername+'&buttonName='+button;
  }
  
  fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((messages) => {
        const messagesContainer = document.getElementById('message-container');
        if (messages.length == 0) {
          messagesContainer.innerHTML = '<p>No messages found</p>';
        } else {
          messagesContainer.innerHTML = '';
        }
        messages.forEach((message) => {
          const messageDiv = buildMessageDiv(message);
          messagesContainer.appendChild(messageDiv);
        });
      });
}





function doSearch()
{
	const content = document.getElementById('search');
	window.location.href = "/user-page.html?user=" + parameterUsername + '&content=' + content.value;

}

/**
 * Builds an element that displays the message.
 * @param {Message} message
 * @return {Element}
 */
function buildMessageDiv(message) {
  const headerDiv = document.createElement('div');
  headerDiv.classList.add('message-header-background');
  headerDiv.appendChild(document.createTextNode(
      message.user + ' - ' + new Date(message.timestamp)));

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('message-background');
  bodyDiv.innerHTML = message.text;

  const messageDiv = document.createElement('div');
  /*messageDiv.classList.add('message-div');*/
  messageDiv.appendChild(headerDiv);
  messageDiv.appendChild(bodyDiv);

  return messageDiv;
}

/** Fetches data and populates the UI of the page. */
function buildUI() {
  setPageTitle();
  showMessageFormIfViewingSelf();
  fetchMessages();
 // fetchAboutMe();
}

function fetchAboutMe(){
  const url = '/about?user=' + parameterUsername;
  fetch(url).then((response) => {
    return response.text();
  }).then((aboutMe) => {
    const aboutMeContainer = document.getElementById('about-me-container');
    if(aboutMe == ''){
      aboutMe = 'This user has not entered any information yet.';
    }
    
    aboutMeContainer.innerHTML = aboutMe;

  });
}

function fetchBlobstoreUrlAndShowForm() {
        fetch('/blobstore-upload-url')
          .then((response) => {
            return response.text();
          })
          .then((imageUploadUrl) => {
            const messageForm = document.getElementById('my-form');
            messageForm.action = imageUploadUrl;
            messageForm.classList.remove('hidden');
          });
      }

/* Return the messages according to button pressed. For example 1 means fetch latest 20 messages.
2 means fetch messages 20 to 40 and so on. User will be able to see the last 100 messages*/

function addPageButtons(){
      fetch("/numberOfMessages").then((response) => {
        return response.text();
      }).then((count) => {
          var buttonCount=parseInt(count)/20;
          //limits maximum pages
          if(buttonCount>5)
            buttonCount=5;
          const pageNumberSection=document.getElementById("messagesPageNumbersSection");
               for(var i=0;i<buttonCount;i++){
                    var button=document.createElement("input");
                    button.type="button";
                    button.value=i+1;
                    button.id=i+1;
                    button.addEventListener("click",function(){
                        fetchMessages(this.id+"");
                    });
                    pageNumberSection.appendChild(button);
               }
      });

}

