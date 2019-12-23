//owo logics, big maths

//Wrap getter to get
function get(id){return document.getElementById(id);}

//Load control
let owoTrack = get('owo-track');
let owoSwitch = get('owo-switch');

//Load in all text containers
let containers = [
get('owo-text'),
get('header-header'),
get('header-download-heading'),
get('header-download-sub'),
get('about-title'),
get('about-comment-one'),
get('about-comment-two'),
get('features-heading'),
get('feature-one'),
get('feature-two'),
get('feature-three'),
get('action-title'),
get('action-download-heading'),
get('action-download-sub'),
get('caption')
];

//owo vs eng
let owo = 0;
let eng = 1;

let lang = [
['owo is OWN', 'owo is OFF'],
['Hit youw goaws', 'Hit your goals'],
['Get goawie', 'Get Goalie'],
['Windows Downwoad', 'Windows Download'],
['A toow fow goaw setting and goaw gwettin', 'A tool for goal setting and goal getting'],
['So fwesh!', 'So fresh!'],
['So cwean!', 'So clean!'],
['Featuwes:', 'Features:'],
['Goaws', 'Goals'],
['Taskwist', 'Tasklist'],
['Fwun to wuse', 'Fun to use'],
['Wat u waiting on?ಠ益ಠ)', 'What are you waiting for?'],
['Get goawie', 'Get Goalie'],
['Windows Downwoad', 'Windows Download'],
['Goawie is a pwoject, not a pwoduct', 'Goalie is a project, not a product']
];

//Handle owo
let isOWO = false;

//Iterative replacement
function swap(owoOn){
  for(let i = 0; i < containers.length; i++){
    if(owoOn){containers[i].innerHTML = lang[i][owo];}
    else{containers[i].innerHTML = lang[i][eng];}
  }
}

//Listener
owoSwitch.addEventListener('click', function(e){
  isOWO = !isOWO;
  if(isOWO){
    owoTrack.classList.replace('owo-track-off', 'owo-track-on');
    owoSwitch.classList.replace('owo-switch-off', 'owo-switch-on');
    owoSwitch.setAttribute('name', 'To Engwish')
    
  }
  else{
    owoTrack.classList.replace('owo-track-on', 'owo-track-off');
    owoSwitch.classList.replace('owo-switch-on', 'owo-switch-off');
    owoSwitch.setAttribute('name', 'Click to owo')
  }
  swap(isOWO);
});

