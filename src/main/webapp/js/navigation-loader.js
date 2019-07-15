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

/**
 * Adds a login or logout link to the page, depending on whether the user is
 * already logged in.
 */
function addLoginOrLogoutLinkToNavigation() {
  const navigationElement = document.getElementById('navigation');
  if (!navigationElement) {
    console.warn('Navigation element not found!');
    return;
  }

  fetch('/login-status')
      .then((response) => {
        return response.json();
      })
      .then((loginStatus) => {
        if (loginStatus.isLoggedIn) {
            navigationElement.appendChild(
              createListItem(createLink('/', 'Home')));
            navigationElement.appendChild(
              createListItem(createLink('/user-page.html?user=' + loginStatus.username, 'Message Board Chat')));
            // navigationElement.appendChild(
            //   createListItem(createLink('/stats.html', 'Statisics')));
            // navigationElement.appendChild(
            //   createListItem(createLink('/community.html', 'Community')));
            // navigationElement.appendChild(
            //   createListItem(createLink('/feed.html', 'Feeds')));
            // navigationElement.appendChild(
            //   createListItem(createLink('/translate-page.html', 'Translate')));
            navigationElement.appendChild(
              createListItem(createLink('/opportunities.html?id=1', 'Opportunity Panel')));
            navigationElement.appendChild(
              createListItem(createLink('/logout', 'Logout')));

        } else {

           navigationElement.appendChild(
              createListItem(createLink('/index.html', 'CS Opportunities Portal for Pakistani Students   ')));
           navigationElement.appendChild(
              createListItem(createLink('/login', 'Login')));
           navigationElement.appendChild(
              createListItem(createLink('/aboutus.html', 'About Us')));
        }
      });
    

}

function addNaviMenues(){
  const navigationElement = document.getElementById('navigation');
  if (!navigationElement) {
    console.warn('Navigation element not found!');
    return;
  }
  addLoginOrLogoutLinkToNavigation();
  }


/**
 * Creates an li element.
 * @param {Element} childElement
 * @return {Element} li element
 */
function createListItem(childElement) {
  const listItemElement = document.createElement('li');
  listItemElement.appendChild(childElement);
  return listItemElement;
}

/**
 * Creates an anchor element.
 * @param {string} url
 * @param {string} text
 * @return {Element} Anchor element
 */
function createLink(url, text) {
  const linkElement = document.createElement('a');
  linkElement.appendChild(document.createTextNode(text));
  linkElement.href = url;
  return linkElement;
}


var myVar;
function loadFunc() {
  myVar = setTimeout(showPage, 700);
}

function showPage() {
  document.getElementById("loader").style.display = "none";
  document.getElementById("content").style.display = "block";
}

function openSlideMenu(){
  document.getElementById('menu').style.width = '250px';
  document.getElementById('content').style.marginLeft = '250px';
  document.getElementById('content').style.opacity = '0.5';
  
}
function closeSlideMenu(){
  document.getElementById('menu').style.width = '0';
  document.getElementById('content').style.marginLeft = '0';
  document.getElementById('content').style.opacity = '1';
}
