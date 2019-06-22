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
    const opportunityContainer = document.getElementById('content');
	var ps = opportunityContainer.getElementsByTagName('p'); //ps contains all of the p elements inside your div
	var p = ps[0]; //take the first element
    if(opportunity == ''){
      opportunity = 'This opportunity does not exist.';
    }
    
    opportunityContainer.innerHTML = opportunity;

  });
}