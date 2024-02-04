const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
          products: [],
          productCategory: [],
          productsSort:[],
          discount: [],
          price: 0,
          productsSale: [],
          productWithDiscount: [],
          isOpen1: false,
          isOpen2: false,
          isOpen3: false,
          isOpen4: false,
          selectedBrand: [],
          filteredBrandProducts: [],
          localStorage: [],
          quantity:1,
          saveQuantity:0,
          localStorageQuantity:0,
          search: "",
          lowStockProduct: [],
          cartItemCount: 0,
          loadingData: true
        }
    },
    created(){
        this.loadData()
  
       
    },

    mounted() {
        this.filterByBrand()
      
    },

    computed: {
        uniqueBrand() {
            return [...new Set(this.products.map(product => product.brand))];
        },
    },

    methods : {
        agregarAlCarrito(product) {
            let storageCarrito = JSON.parse(localStorage.getItem("carrito")) || [];

            
          let carrito = this.checket(product)
           if(!carrito){
            storageCarrito.push({productId: product.id, quantity: this.quantity});
            this.saveQuantity = this.quantity+1
               
                console.log(product);
            }

                
            
            localStorage.setItem("carrito", JSON.stringify(storageCarrito))
            this.localStorage = storageCarrito
    

        },
        checket(product){
            let storageCarrito = JSON.parse(localStorage.getItem("carrito")) || [];
            return storageCarrito.some(item => item === product.id)
        },
        loadData() {
            axios.get("/api/products")
                .then(response => {
                    this.products = response.data
                    this.filteredBrandProducts = this.products
                    this.productsSort = response.data.sort((a, b) => { return a.id - b.id })
                    this.loadingData = !this.loadingData
                    console.log(this.productsSort)
                    this.lowStockProduct= this.products.filter(product => {return product.stock <= 5})
                    console.log(this.lowStockProduct)
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

        productByCategory(category) {
            axios.get("/api/products" + category)
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

        categoryFilter(event) {
            const ALL = document.getElementById("ALL")
            const CPU = document.getElementById("CPU")
            const MONITOR = document.getElementById("MONITOR")
            const KEYBOARD = document.getElementById("KEYBOARD")
            const MOUSE = document.getElementById("MOUSE")
            const MOTHERBOARD = document.getElementById("MOTHERBOARD")
            const HEADPHONES = document.getElementById("HEADPHONES")
            const ACCESSORIES = document.getElementById("Accesories")

            if (event.target === ALL) {
                this.productByCategory("?")
            } else if (event.target === CPU) {
                this.productByCategory("/filtered?category=CPU")
            } else if (event.target === MONITOR) {
                this.productByCategory("/filtered?category=MONITOR")
            } else if (event.target === KEYBOARD) {
                this.productByCategory("/filtered?category=KEYBOARD")
            } else if (event.target === MOUSE) {
                this.productByCategory("/filtered?category=MOUSE")
            } else if (event.target === MOTHERBOARD) {
                this.productByCategory("/filtered?category=MOTHERBOARD")
            } else if (event.target === HEADPHONES) {
                this.productByCategory("/filtered?category=HEADPHONES")
            } else if (event.target === ACCESSORIES) {
                this.productByCategory("/filtered?category=Accesories")
            }
        },
        filterByBrand() {
            this.filteredBrandProducts = this.products.filter(product => {
                return this.selectedBrand.length === 0 || this.selectedBrand.some(brand => product.brand.toLowerCase().toUpperCase() === brand.toLowerCase().toUpperCase());
            });
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
        },

        saveSearch(event) {
            this.search = event.target.value
            this.searchFilter()
        },

        searchFilter() {
            this.filteredBrandProducts = this.products.filter(product => product.name.toLowerCase().includes(this.search.toLowerCase()))
        },
        
    }

})

app.mount("#app")