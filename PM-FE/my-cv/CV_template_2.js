

fetch(`http://localhost:9002/api/v1/profile`)
.then(res => console.log(res.json()))
.then(data => console.log(data))
.catch(e => console.log(e))

console.log("hello")




  
