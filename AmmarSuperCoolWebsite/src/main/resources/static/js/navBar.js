console.log("dom Connected")
let btn = document.querySelector("#btn");
let sidebar = document.querySelector(".sidebar");
let search = document.querySelector(".bx-search");

btn.onclick = function (){
    sidebar.classList.toggle("active")
}

search.onclick = function (){
    sidebar.classList.toggle("active")
}