const { createApp } = Vue

const options = {
  data() {
    return {
      productos: [],
    
      localStorageCart: [],
      modalHVisible:false,
      modalVisibleAlert:false,
      items:[],
      comment:'',
      discount:0,
      itemsFiltrados:[],
    
    }
  },

  created() {
    this.loadData()
      

    
   
  },//Create
  methods: {
    loadData() {
     
      axios.get('/api/products')
        .then(data => {
          this.productos = data.data
          this.localStorageCart = JSON.parse(localStorage.getItem('carrito')) || [];
          console.log(this.localStorageCart)
          // Filtra los artículos que estan en el carrito
         this.localStorageFiltrado()
         this.addQuantity()
    
      
    
          //itemsFiltrados = localStorageCart.map(item => ({ productId: item.id, quantity: item.cantidad }))
          // Filtra los artículos que estan en el carrito
          // console.log(this.localStorageCart)
          // this.localStorageFiltrado = this.productos.filter(producto =>
          //   this.localStorageCart.some(storage => storage.id === producto.productId))
          // // Agrega la cantidad del carrito a cada artículo filtrado
        
          // console.log(this.localStorageFiltrado)
        })
        .catch(error => console.log(error))
    },
    addQuantity() {
      this.itemsFiltrados.forEach(producto => {
        const cantidadEnCarrito = this.localStorageCart.find(storage => storage.productId === producto.id)
        if (cantidadEnCarrito) {
        producto.cantidadEnCarrito = cantidadEnCarrito.quantity
        console.log(producto.cantidadEnCarrito)
       }
      })
      
    },
    localStorageFiltrado(){
     this.itemsFiltrados= this.productos.filter(producto => this.localStorageCart.some(storage => storage.productId === producto.id))
     console.log(this.itemsFiltrados)
    },
    checkout(){
      const body = {
        items: itemsFiltrados,
        comment: this.comment,
        discount: this.discount
        
      }
      axios.post("/api/checkout/",body)
      .then(response => {
        console.log(response)
      })
      .catch(error => console.log(error))
    },
  
    removerDelCarro(producto, accion) {
      let storageCarrito = JSON.parse(localStorage.getItem('carrito')) || [];
      const index = storageCarrito.findIndex(item => item.id === producto.id);

      if (accion === 'restar') {
        this.restar(producto);
      } else if (accion === 'sumar') {
        this.sumar(producto);
      }

      // Actualiza la cantidad en el localStorage
      if (index !== -1) {
        storageCarrito[index].cantidad = producto.cantidadEnCarrito;

        // Elimina el elemento si la cantidad es cero
        if (producto.cantidadEnCarrito === 0) {
          storageCarrito.splice(index, 1);
        }
      } else if (producto.cantidadEnCarrito > 0) {
        // Agrega el elemento solo si la cantidad es mayor a cero
        storageCarrito.push({ id: producto.id, cantidad: producto.cantidadEnCarrito });
      }
      localStorage.setItem('carrito', JSON.stringify(storageCarrito));
      this.localStorage = storageCarrito
    }, //aca termina removerDelCarro
    totalPorproducto(producto) {
      let total = producto.cantidadEnCarrito * producto.price
      return this.dotsNumbers(total)
    }, // finaliza totalPorproducto
    restar(producto) {
      if (producto.cantidadEnCarrito > 0) {
        producto.cantidadEnCarrito--
      }
    },//finaliza restar
    sumar(producto) {
      if (producto.cantidadEnCarrito < producto.stock) {
        producto.cantidadEnCarrito++
      }
    },// finaliza sumar
    dotsNumbers(number) {
      // se verifica que los datos sean distinto de undefined y null
      // luego se usa el metodo toLocaleString que convierte
      // un string segun la region establecida, se le da
      // el stylo de moneda y se selecciona USD como moneda
      //ademas se establece que no debe mostrar numeros fraccionados.
      if (number !== undefined && number !== null) {
        return number.toLocaleString("es-MX", {
          style: "currency",
          currency: "USD",
          minimumFractingDigits: 0,
        })
      }
    },//fin del dotsNumbers
    graciasPorSuCompra() {
      alert("Thanks for your purchase!")
    }, //finaliza graciasporsucompra
    //inicia menu hamburguesa
    abrirModalHamb() {
      this.modalHVisible = true
    },
    cerrarModalHamb() {
      this.modalHVisible = false
    },
    //finaliza menu hamburguesa
    abrirAlert() {
      this.modalVisibleAlert = true
      if (this.modalVisibleAlert) {
          document.body.classList.add('overflow-y-hidden')
      }
  }, // finaliza showModal
  cerrarAlert() {
      this.modalVisibleAlert = false
      if (this.modalVisibleAlert == false) {
          document.body.classList.remove('overflow-y-hidden')
      }
  },// finaliza cerrarModal
  },//finaliza methods
  computed: {
    totalCarrito() {
      let total = 0
      for (let i = 0; i < this.itemsFiltrados.length; i++) {
        const producto = this.itemsFiltrados[i]
        total += producto.cantidadEnCarrito * producto.price
      }
      return this.dotsNumbers(total)
    }, //aca finaliza totalCarrito
  },//aca finaliza el computed
}//finalizacion de options

const app = createApp(options)
app.mount('#app')
