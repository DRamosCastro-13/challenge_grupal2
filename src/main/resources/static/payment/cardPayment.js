const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            number:"",
            cvv: 0,
            amount:0,
            description:"",
            e:"",
            cart:JSON.parse(localStorage.getItem('carrito')) || [],
            
        }
    },
    created(){
       
    },

    methods : {
      traerAmount(){},
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
                    axios.post("http://localhost:8081/api/cards/payments", body)
                    .then(result => {Swal.fire({
                            title: "Successful payment!",
                            text: "",
                            icon: "success",
                            confirmButtonColor: "#E6A51D",
                          }).then((result) => {
                            console.log(result)
                              const cart = {
                                items: this.cart,
                                comment: this.comment,
                                discount: this.discount
                              }
                              axios.post("/api/checkout",cart)
                              .then(response => {
                                console.log(response)
                              })
                              .catch(error => console.log(error))
                            
                          
                          })
                        })
                    .catch(error => {
                      /*Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: this.e = error,
                        confirmButtonColor: "#E6A51D"
                      });*/
                    console.log(error)
                    })
                }
              })
        }
    }

})

app.mount("#app")