const regexPhone = /^0[3798][0-9]{8}$/;
const regexEmail = /^.{5,12}\@gmail\.com$/;
const regexName = /^([a-zA-Z]+( [a-zA-Z]+)*)$/;
const regexLength=/^.{1,30}$/;
let regex = [regexName, regexEmail, regexPhone, regexName];
for(let i=0; i<document.getElementsByTagName("input").length; i++){
    let inputName = document.getElementsByTagName("input")[i];
    document.querySelector("#"+inputName.id).addEventListener("input",(e)=>{
        if(!regex[i].exec(inputName.value) || !regexLength.exec(inputName.value)){
            document.getElementById(inputName.name).style.borderColor = "red";
            document.querySelector(".error-"+inputName.name).innerHTML=inputName.name+" is not valid";
        }else{
            document.getElementById(inputName.name).style.borderColor = "green";
            document.querySelector(".error-"+inputName.name).innerHTML="";
        }
    })
}