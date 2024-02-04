const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
        products: [],
        id: "",
        product: {},
        productsSale:[],
        loadingData: true
        
          
            
            
        }
    },
    created(){
        this.id=new URLSearchParams(window.location.search).get("id")
        this.loadData()
       
    },

    methods : {
        loadData(){
            axios.get("/api/products")
            .then(response => {
                this.products = response.data
                this.product = this.products.find(product => product.id == this.id)
                console.log(this.product)
                this.loadingData = !this.loadingData
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