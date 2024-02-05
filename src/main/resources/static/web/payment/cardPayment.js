const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            number:"",
            cvv: 0,
            amount:0,
            description:"",
            e:"",
            cart: JSON.parse(localStorage.getItem('carrito')) || [],
            totalAmount: JSON.parse(localStorage.getItem('amount')),

            
        }
    },
    created(){
      const urlParams = new URLSearchParams(window.location.search);
      const totalCarrito = urlParams.get('total');
      this.amount = parseFloat(totalCarrito) || 0;
    },

    methods : {
      formatTarjeta() {
        // Elimina caracteres no numéricos
        let inputValue = this.number.replace(/\D/g, '');
        // Formatea el número de tarjeta con un guion cada 4 dígitos
        let formattedValue = inputValue.replace(/(\d{4})/g, '$1-');
        // Elimina el guion adicional al final, si lo hay
        formattedValue = formattedValue.replace(/-$/, '');
        // Actualiza el valor del campo de entrada
        this.number = formattedValue;
      },
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
                      "amount" : this.totalAmount,
                      "description" : this.description
                  }
                    console.log(body)
                    axios.post("https://opythabank.onrender.com/api/cards/payments", body)
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
                              .finally(() =>{
                                localStorage.removeItem('carrito')
                                localStorage.removeItem('amount')
                              })
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