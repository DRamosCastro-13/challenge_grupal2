const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
        id: "",
        product: {},
        isLoggedIn: false,
        showDropdown: false,
        error: '', 
        loadingData:true,
        }
    },
    created(){
        const search = location.search
        const url = new URLSearchParams(search)
        this.id = url.get("id")
        this.loadData()
        this.checkLogin()
        console.log(this.id)
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
            axios.get("/api/products/"+this.id)
            .then(response => {
              this.product = response.data
              console.log(this.product)
              this.loadingData = !this.loadingData
                // this.product = response.data.some(product => this.id === product.id)
                // console.log(this.product)
                // this.productsSale=this.products.filter(product => product.discount > 0)
                // this.productWithDiscount=this.productsSale.forEach(product =>  { 
                //                                     const sale= product.price / 100 * product.discount
                //                                     const newPrice= product.price - sale
                //                                     product.discount=newPrice
                //                                     console.log(product)
                //                                     return product })
                // console.log(this.productsSale)
            })
            .catch(error => {
                console.log(error)
            })
          }
    }

})

app.mount("#app")