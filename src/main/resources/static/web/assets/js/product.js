const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            products: [],
      productCategory: [],
          productsSort:[],
          isOpen1: false,
          isOpen2: false,
          isOpen3: false,
          isOpen4: false,
          localStorage: [],
          quantity:1,
          saveQuantity:0,
            
            
        }
    },
    created(){
        this.loadData()
       
    },

    methods : {
        agregarAlCarrito(product) {
            let storageCarrito = JSON.parse(localStorage.getItem("carrito")) || [];

            
          let carrito = this.checket(product)
           if(!carrito){
            storageCarrito.push({productId: product.id, quantity: this.quantity});
            this.saveQuantity = this.quantity+1
               
                console.log(product);
                Swal.fire({
                    position: "top-end",
                    icon: "success",
                    title: "Item added to cart",
                    showConfirmButton: false,
                    timer: 1500
                  });
            }

                
            
            localStorage.setItem("carrito", JSON.stringify(storageCarrito))
            this.localStorage = storageCarrito
    

        },
        checket(product){
            let storageCarrito = JSON.parse(localStorage.getItem("carrito")) || [];
            return storageCarrito.some(item => item === product.id)
        },
        loadData(){
            axios.get("/api/products")
            .then(response => {
                this.products = response.data
                this.productsSort=response.data.sort((a, b) => {return a.id - b.id})
                console.log(this.productsSort)
            })
            .catch(error => {
                console.log(error)
            })
          },

          productByCategory(){
            axios.get("/api/products/filtered?productCategory=" + this.productCategory)
          },

          dropDownMenu1() {
            this.isOpen1 = !this.isOpen1;
        },
        dropDownMenu2() {
            this.isOpen2 = !this.isOpen2;
        },
        dropDownMenu3() {
            this.isOpen3 = !this.isOpen3;
        },
        dropDownMenu4() {
            this.isOpen4 = !this.isOpen4;
        }
        
    }

})

app.mount("#app")