const { createApp } = Vue

let app = createApp({

    data() {
        return {
            products: [],
            productCategory: [],
            productsSort: [],
            discount: [],
            price: 0,
            productsSale: [],
            productWithDiscount: [],
            isOpen1: false,
            isOpen2: false,
            isOpen3: false,
            isOpen4: false,
            selectedBrand: [],
            
        }
    },
    created() {
        this.loadData()

    },

    mounted() {
    
    },

    computed: {
      productsWithDiscounts() {
        return this.products.filter(product => product.discount > 0);
      }
    },

    methods: {
        loadData() {
            axios.get("/api/products")
                .then(response => {
                    this.products = response.data
                    this.filteredBrandProducts = this.products
                    this.productsSort = response.data.sort((a, b) => { return a.id - b.id })
                    console.log(this.productsSort)
                    this.productsSale = this.products.filter(product => product.discount > 0)
                    this.productWithDiscount = this.productsSale.forEach(product => {
                        const sale = product.price / 100 * product.discount
                        const newPrice = product.price - sale
                        product.discount = newPrice
                        console.log(product)
                        return product
                    })
                    console.log(this.productsSale)

                })
                .catch(error => {
                    console.log(error)
                })
        },



        
    } // fin methods
}) // fin create app

app.mount("#app")