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