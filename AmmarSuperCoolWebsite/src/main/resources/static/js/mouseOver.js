console.log("dom Connected");

document.addEventListener("DOMContentLoaded", init);

function init() {

    // get all the elements with a class name of clink
    let links = document.getElementsByClassName("clink"), wait;

    for (let course of links) {
        let a;
        course.addEventListener("mouseout", function (){
            a.remove();
        });
        course.addEventListener("mouseover", function() {
            // invoke the handler that gets a Course by id
            fetch('/course/' + course.id)
                .then(data => data.json()) // convert to json

                // function to execute on the json data
                .then(function(data) {
                    let output = "Course code: " + data.code + "<br>" +
                        "Title: " + data.title + "<br>" +
                        "Credits: " + data.credits + "<br>" +
                        "Completed: " + data.complete + "<br>" +
                        "Term: " + data.term + "<br>" +
                        "Final Grade: " + data.finalGrade;

                    a = createElement("div", course.parentElement);
                    a.setAttribute("id", "overlay")
                    a.innerHTML = output;
                    console.log("works");
                });
        });
    }
}


function createElement(tag, parent) {
    let temp = document.createElement(tag)
    parent.appendChild(temp)
    return temp
}
