import { Component, OnInit } from '@angular/core';
import { Navbar } from '../navbar/navbar/navbar';
import { CommonModule } from '@angular/common';
import { Productservice } from '../service/productservice';
import { CartService } from '../service/cart-service';
import { OrderItem } from '../models/order-item.model';
import { ProdCategory, Product, SubCategory } from '../models/product.model';
import { FormsModule, NgModel } from '@angular/forms';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';
import { Footer } from "../footer/footer";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ CommonModule, FormsModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home implements OnInit {

  constructor(private productservice: Productservice, private cartservice: CartService, private authservice: AuthService, private router: Router) {}

  immagini = [
    "assets/limoniCanva.png",
    "assets/mandarini.jpg",
    "assets/aranceCanva.png",
  ]

  currentindex=0;

  quantita=0;

  products: Product[] = [];
  prodottiscelti: Product[]=[]

  categorie = [ProdCategory.ARANCE, ProdCategory.LIMONI, ProdCategory.MANDARINI];

  sottocategorie: Record<ProdCategory, SubCategory[]> = {
  [ProdCategory.ARANCE]:    [SubCategory.BIONDE, SubCategory.ROSSE],
  [ProdCategory.LIMONI]:    [],
  [ProdCategory.MANDARINI]: []
};

  NomeProdotto='';
  
  categoriaScelta?:ProdCategory
  sottocategorieScelte: SubCategory[]= []

  searchByCategory(categoria: ProdCategory, sottocategoria?: SubCategory) {
  this.categoriaScelta = categoria;
  this.sottocategorieScelte = this.sottocategorie[categoria];

  if (!sottocategoria) {
    this.productservice.searchByCategory(categoria).subscribe({
      next: (data: Product[]) => {
        this.products = data;
      },
      error: err => console.error(err)
    });
    return;
  }

  this.productservice.searchByCategory(categoria, sottocategoria).subscribe({
    next: (data: Product[]) => {
      this.products = data;
    },
    error: err => console.error(err)
  });
}

  searchByName(nome: string): void {

  this.productservice.searchByName(nome).subscribe({

    next: (data: Product[]) => {

      this.products = data;

      if(data.length === 0){

        this.showToast(
            'Nessun prodotto trovato'
        );
      }
    },

    error: (err) => {

      console.error(err);

      this.showToast(
          'Errore durante la ricerca'
      );
    }
  });
}




  ngOnInit(){
    this.loadProducts();
    setInterval(() => {
      if(this.currentindex<2){
        this.currentindex= this.currentindex+1
      }
      else
        this.currentindex=0
    }, 5000); // 30 secondi
    }

    loadProducts() {
    this.productservice.getProducts().subscribe({
      next: data => {
        this.products = data;
        console.log("Prodotti ricevuti:", data);
      },
      error: err => console.error("Errore caricamento prodotti:", err)
    });
  }


    add(product: Product, quantity: number) {

  if(this.authservice.isLogged()){

    const orderItem: OrderItem = {
      product: product,
      quantity: quantity,
      price: product.price * quantity
    };

    this.cartservice.add(orderItem).subscribe({

      next: () => {

        this.showToast('Aggiunto al carrello');
      },

      error: (err) => {

        console.error(err);

        this.showToast(err.error.message);
      }
    });

  } else {

    this.router.navigate(['login']);
  }
}


    toastVisible=false
    toastMessage=''
    private toastTimeout: any;

    showToast(message: string){
      this.toastMessage=message;
      this.toastVisible=true;

      if (this.toastTimeout) {
      clearTimeout(this.toastTimeout);
      }

    this.toastTimeout = setTimeout(() => {
      this.toastVisible = false;
    }, 2000);
    }
  }
