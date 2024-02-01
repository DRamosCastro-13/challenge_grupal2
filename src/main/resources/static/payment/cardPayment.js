const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            number:"",
            cvv: 0,
            amount:0,
            description:"",
            e:""
            
        }
    },
    created(){
       
    },

    methods : {
        cardPayment(){
            Swal.fire({
                title: "Are you sure?",
                text: "Verify that all data is correct!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#7ACED7",
                cancelButtonColor: "#EEFFFD",
                confirmButtonText: "Yes, send money!"
              }).then((result) => {
                if (result.isConfirmed) {
                    const body = {
                        "number" : this.number,
                        "cvv" : this.cvv,
                        "amount" : this.amount,
                        "description" : this.description
                    }
                    console.log(body)
                    axios.post("https://thebalooninc.onrender.com/api/cards/payments", body)
                    .then(result => {Swal.fire({
                            title: "Successful payment!",
                            text: "",
                            icon: "success",
                            confirmButtonColor: "#E6A51D",
                          }).then((result) => {
                            console.log(result)
                          })
                        })
                    .catch(error => {Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: this.e = error.response.data,
                        confirmButtonColor: "#E6A51D"
                      });
                    console.log(error)
                    })
                }
              })
        }
    }

})

app.mount("#app")