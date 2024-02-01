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
          isOpen4: false
            
            
        }
    },
    created(){
        this.loadData()
       
    },

    methods : {
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