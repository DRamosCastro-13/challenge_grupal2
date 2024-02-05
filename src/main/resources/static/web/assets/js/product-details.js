const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
        products: [],
        id: "",
        product: {},
        productsSale:[],
        isLoggedIn: false,
        showDropdown: false,
        error: '', 
            
        }
    },
    created(){
        this.id=new URLSearchParams(window.location.search).get("id")
        this.loadData()
        this.checkLogin()
       
    },

    methods : {
        checkLogin() {
            axios.get('/api/clients/current')
              .then(response => {
                if (response.data.role == "CLIENT" || response.data.role == "ADMIN") {
                  this.isLoggedIn = true;
                }
                else {
                  this.isLoggedIn = false;
                }
              })
              .catch(error => {
                console.error("Error loading user data, please login", error);
              });
          },
          logout() {
            axios.post("/api/logout")
                .then(response => {
                    window.location.href = "/index.html";
                })
                .catch(error => {
                    console.log(error);
                });
          },
          toggleDropdown() {
            this.showDropdown = !this.showDropdown;
          },
        loadData(){
            axios.get("/api/products")
            .then(response => {
                this.products = response.data
                this.product = this.products.find(product => product.id == this.id)
                console.log(this.product)
                this.productsSale=this.products.filter(product => product.discount > 0)
                this.productWithDiscount=this.productsSale.forEach(product =>  { 
                                                    const sale= product.price / 100 * product.discount
                                                    const newPrice= product.price - sale
                                                    product.discount=newPrice
                                                    console.log(product)
                                                    return product })
                console.log(this.productsSale)
            })
            .catch(error => {
                console.log(error)
            })
          }
    }

})

app.mount("#app")