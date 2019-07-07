const urlParams = new URLSearchParams(window.location.search);
const parameterId = urlParams.get('id');

// URL must include ?id=XYZ parameter. If not, redirect to homepage.
if (!parameterId) {
  window.location.replace('/');
}


function fetchOpportunities(){
	console.log("Function called");
  const url = '/getOpportunity?id=' + parameterId;
  fetch(url).then((response) => {
    return response.text();
  }).then((opportunity) => {
    console.log(opportunity)
    //front end build here
  });
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

function displayTitles(){
  fetch('/map')
  .then(data=>{ 
    return data.json()
  })
  .then(data=>{	
    // console.log(data)
    var opportunitiesTitles = data[1];
    var opportunitiesDueDate = data[2];
    var opportunitiesRecuurintStatus = data[3];
    for (let index = 0; index < opportunitiesTitles.length; index++) {
      var date = new Date (opportunitiesDueDate[index])
      var today = new Date();
      var givenClassID = "active-list" 

      if (date.getTime() > today.getTime()){
        givenClassID = "active-list"
      } else if (date.getTime() < today.getTime()) {
        if (opportunitiesRecuurintStatus[index] === true){
          givenClassID = "recurring-list";
        } else {
          givenClassID = "archived-list";
        }
      }
      id = index + 1;
      var menu = document.getElementById(givenClassID)
      var title = document.createElement('li')
      title.innerHTML = "<a href='opportunities.html?id=" + id + "' > " + opportunitiesTitles[index] + "</a>"
      menu.appendChild(title);
    };
  })
}

const displayOpportunity = () => {
  const urlParams = new URLSearchParams(window.location.search);
  const parameterId = urlParams.get('id');
  const url = '/getOpportunity?id=' + parameterId;
  fetch(url).then((response) => {
    return response.json();
  }).then((opportunity) => {
    document.getElementById('title').innerHTML = opportunity.title
    document.getElementById('due-date').innerHTML = opportunity.dueDate
    document.getElementById('img').src = opportunity.advertisementImageUrl
    document.getElementById('desc').innerHTML = opportunity.description
    document.getElementById('applyLink').href = opportunity.applyLink
    
    var detailsContainer = document.getElementById("details")
    var detailsUL = detailsContainer.getElementsByTagName("ul")[0]
    
    var detailPoint = document.createElement("li")
    detailPoint.innerHTML = "Location: " + opportunity.city + ", " + opportunity.country 
    detailsUL.appendChild(detailPoint)
    
    var detailPoint = document.createElement("li")
    detailPoint.innerHTML = "Start Date: " + opportunity.startDate 
    detailsUL.appendChild(detailPoint)
    
    var details = opportunity.opportunityDetails
    details.forEach(detail=>{
      var detailsContainer = document.getElementById("details")
      var details = detailsContainer.getElementsByTagName("ul")[0]
      var detailPoint = document.createElement("li")
      detailPoint.innerHTML = detail
      details.appendChild(detailPoint)
    });

    var reqContainer = document.getElementById("other-req")
    var reqs = reqContainer.getElementsByTagName("ul")[0]
    var reqPoint = document.createElement("li")
    reqPoint.innerHTML = "Minimum Age: " + opportunity.minAge
    reqs.appendChild(reqPoint)

    var reqPoint = document.createElement("li")
    reqPoint.innerHTML = "Maximum Age: " + opportunity.maxAge
    reqs.appendChild(reqPoint)

    var reqPoint = document.createElement("li")
    reqPoint.innerHTML = "Education Level: " + opportunity.eductationLevel
    reqs.appendChild(reqPoint)

    var reqPoint = document.createElement("li")
    reqPoint.innerHTML = "Open For Gender(s): " + opportunity.gender
    reqs.appendChild(reqPoint)


    var otherRequirments = opportunity.otherRequirments
    otherRequirments.forEach(req=>{
      // console.log(req);
      var reqContainer = document.getElementById("other-req")
      var reqs = reqContainer.getElementsByTagName("ul")[0]
      var reqPoint = document.createElement("li")
      reqPoint.innerHTML = req
      reqs.appendChild(reqPoint)
    });
    var additionalLinks = opportunity.additionalLinks
    additionalLinks.forEach(link=>{
      // console.log(link);
      var linkContainer = document.getElementById("additional-links")
      var links = linkContainer.getElementsByTagName("ul")[0]
      var linkPoint = document.createElement("li")
      linkPoint.innerHTML = link
      links.appendChild(linkPoint)
    });
  });
}

function searchFunction() {
  // Declare variables
  var input, filter, ul, li, a, i, txtValue;
  input = document.getElementById('myInput');
  filter = input.value.toUpperCase();
  isEmpty = input.value.length;
  console.log(isEmpty)
  var ULs = document.getElementsByClassName('search');
  // console.log(ULs)
  for (j = 0; j < ULs.length; j++) {
    ul = ULs[j]
    var li = ul.getElementsByTagName('li'); 
    var queryResult = 0
    var nonZero = 0
    // Loop through all list items, and hide those who don't match the search query
    for (i = 0; i < li.length; i++) {
      a = li[i].getElementsByTagName("a")[0];
      txtValue = a.textContent || a.innerText;
      queryResult = (txtValue.toUpperCase().indexOf(filter) > -1)
      if (queryResult) {
        li[i].style.display = "";
        nonZero = 1
      } else {
        li[i].style.display = "none";
      }
    }
    var parent = ul.parentElement.previousElementSibling.classList
    if (nonZero && isEmpty !== 0){
      parent.add("active");
      // parent.add("touch")
    }
    else if (li.length > 0 && isEmpty == 0 && !parent.contains("touch")) {
      parent.remove("active");
    }
  } 
}


var myVar;
    
function loadFunc() {
  myVar = setTimeout(showPage, 700);
}

function showPage() {
  document.getElementById("loader").style.display = "none";
  document.getElementById("content").style.display = "block";
}

var active = false

function drop() {
  var dropdown = document.getElementsByClassName("dropdown-btn");

  for (var i = 0; i < dropdown.length; i++) {
    dropdown[i].addEventListener("click", function() {
      if (this.classList.contains("touch")) {
        this.classList.remove("touch")
        this.classList.remove("active")
      }
      else {
        this.classList.add("touch")
        this.classList.add("active")
      }
      // this.classList.toggle("active");
      var dropdownContent = this.nextElementSibling;
      if (dropdownContent.style.display === "block") {
        dropdownContent.style.display = "none";
      } else {
        dropdownContent.style.display = "block";
      }
    });
  }
} 

function startFunctions(){
  loadFunc(); 
  displayTitles(); 
  displayOpportunity(); 
  drop()
}