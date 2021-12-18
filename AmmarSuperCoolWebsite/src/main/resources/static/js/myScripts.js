console.log("dom Connected");

document.addEventListener("DOMContentLoaded", init);

function init() {

    // get all the elements with a class name of clink
    let links = document.getElementsByClassName("clink");

    for (let course of links) {
        let a;
        let stmt = false;
        course.addEventListener("click", function() {
            stmt = !stmt;

            if(stmt){
            // invoke the handler that gets a Course by id
            fetch('http://localhost:8080/course/' + course.id)
                .then(data => data.json()) // convert to json

                // function to execute on the json data
                .then(function(data) {


                    let output = "Course code: " + data.course.code + "<br>" +
                        "Title: " + data.course.title + "<br>" +
                        "Credits: " + data.course.credits + "<br>" +
                        "Completed: " + data.course.complete + "<br>" +
                        "Term: " + data.course.term + "<br>" +
                        "Final Grade: " + data.course.finalGrade;

                    a = createElement("div", course.parentElement);
                    a.setAttribute("id", "overlay")
                    a.innerHTML = output;
                    console.log("works");


                });
            }else {
                a.innerHTML="";
                a.remove();
            }
        });
    }
}

function createElement(tag, parent) {
    let temp = document.createElement(tag)
    parent.appendChild(temp)
    return temp
}
