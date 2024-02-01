const {createApp} = Vue

let app = createApp({
    data(){
        return {
            products : [],
            error: ''
           
        }
    },
    created(){
        this.loadData()
        
    },

    methods: {
        loadData(){
            axios.get("/api/products")
            .then(response => {
                this.products = response.data
                console.log(this.products)
            })
            .catch(error => {
                console.log(error)
            })
        },
        logout(){
            axios.post("/api/logout")
            .then(response => {
                window.location.href="/index.html"
            }).catch(error => {
                console.log(error)
            })
        },
        clearData(){
            this.error = ''
        }
    }
}).mount('#app')
