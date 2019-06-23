function fetchMessages(){
    fetch('/getAllOpportunities')
    .then(function (response) {
        return response.json(); 
    })
    .then(function (data) {
        appendData(data);
    })
    .catch(function (err) {
        console.log('error: ' + err);
    });
    
    function appendData(data) {
    var mainContainer = document.getElementById("main-container");
    mainContainer.innerHTML = '';

    data.forEach(object => {
        const opportunityDiv = buildOpportunityDiv(object);
        mainContainer.appendChild(opportunityDiv);
    });
    }
}
function buildOpportunityDiv(element){



const opportunityTitle = document.createElement('div');
opportunityTitle.classList.add("col-lg-9");
opportunityTitle.classList.add("h1");

opportunityTitle.appendChild(document.createTextNode(element.title));

const dueDateDiv = document.createElement('div');
dueDateDiv.classList.add('text-center');
dueDateDiv.classList.add("col-lg-3");
dueDateDiv.classList.add('alert')
dueDateDiv.classList.add('alert-danger')
dueDateDiv.appendChild(document.createTextNode("Due Date: " + element.dueDate));

const headerDiv = document.createElement('div');
headerDiv.classList.add('row');

headerDiv.appendChild(opportunityTitle);
headerDiv.appendChild(dueDateDiv);

const genderDiv = document.createElement('div');
genderDiv.classList.add('col-lg-4') 
genderDiv.innerHTML = "Open     For: " + element.gender
const ageGroup = document.createElement('div');
ageGroup.classList.add('col-lg-4')

ageGroup.innerHTML = "Age Group: " + element.minAge + "-" + element.maxAge

const educationDiv = document.createElement('div');
educationDiv.classList.add('col-lg-4')
educationDiv.innerHTML = "Minimum Qualification: " + element.eductationLevel


const elegibilityDiv = document.createElement('div');
elegibilityDiv.classList.add('alert');
elegibilityDiv.classList.add('alert-info');
elegibilityDiv.classList.add('strong');
elegibilityDiv.classList.add('text-center');

elegibilityDiv.classList.add('row');
elegibilityDiv.appendChild(educationDiv);
elegibilityDiv.appendChild(genderDiv);
elegibilityDiv.appendChild(ageGroup);

const applyDiv = document.createElement('button');
applyDiv.classList.add('btn');
applyDiv.classList.add('btn-primary'); 
applyDiv.classList.add('btn-block');
// applyDiv.appendChild(document.createTextNode(element.applyLink))
applyDiv.innerHTML = "Apply Now";
applyDiv.formAction = element.applyLink;


const bodyDiv = document.createElement('div');
bodyDiv.classList.add('alert');
bodyDiv.classList.add('row');
bodyDiv.classList.add('alert-warning');
bodyDiv.classList.add('text-justify');
// bodyDiv.appendChild(document.createTextNode(message.text));
bodyDiv.innerHTML = element.description;

const opportunityDiv = document.createElement('div');
opportunityDiv.classList.add("container");
opportunityDiv.appendChild(headerDiv);
opportunityDiv.appendChild(elegibilityDiv)
opportunityDiv.appendChild(bodyDiv);
opportunityDiv.appendChild(applyDiv);
opportunityDiv.appendChild(document.createElement('div')).classList.add('page-header')


return opportunityDiv;
}

// Fetch data and populate the UI of the page.
function buildUI(){
fetchMessages();
}