import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../service/profile-service';
import { User } from '../models/user.models';
import { RouterLink } from "@angular/router";
import { CommonModule } from '@angular/common';
import { ProdCategory, Product, SubCategory } from '../models/product.model';
import { ProductRequest } from '../models/product-request.models';
import { AdminService } from '../service/admin-service';
import { FormsModule } from '@angular/forms';
import { Productservice } from '../service/productservice';
import { Order, OrderStatus } from '../models/order.models';


@Component({
  selector: 'app-profilo',
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './profilo.html',
  styleUrl: './profilo.css'
})
export class Profilo implements OnInit{

  constructor(private profileservice: ProfileService, private adminservice: AdminService, private productservice: Productservice){}

  utente?: User;

  showPanel=false;

  public activeTab:
  | 'MieiDati'
  | 'Admin'
= 'MieiDati';

  mostraPannello(){
    this.showPanel=!this.showPanel;
  }

  ngOnInit(): void {
    this.getCredential()
    this.productservice.getProducts().subscribe({
      next: data =>{
        this.listaprodotti=data;
      }
    }
    )
  }

  spesaTot(): number {

  let totale = 0;

  if(this.utente?.orderList){

    for(const ordine of this.utente.orderList){
      if(ordine.orderStatus !== OrderStatus.CANCELLATO){
        totale += ordine.total;
      }
    }
  }
  return totale;
}

  getCredential(){
    this.profileservice.getMyCredential().subscribe({
      next: data => {
        this.utente=data;
        console.log(data);
      }
    })
  }


  categorie = [ProdCategory.ARANCE, ProdCategory.LIMONI, ProdCategory.MANDARINI];

  sottocategorie: Record<ProdCategory, SubCategory[]> = {
  [ProdCategory.ARANCE]:    [SubCategory.BIONDE, SubCategory.ROSSE],
  [ProdCategory.LIMONI]:    [],
  [ProdCategory.MANDARINI]: []
};

  listaprodotti: Product[]=[];

 sottoCategorieDaScegliere(indice: string): SubCategory[] {
  const chiave= this.categorie[Number(indice)];
  console.log(chiave)
  console.log(this.sottocategorie[chiave])
  return this.sottocategorie[chiave];
}

  sottoCategorieSelezionabili: SubCategory[] = [];

onCategoriaChange(cat: string) {
  this.sottoCategorieSelezionabili = this.sottoCategorieDaScegliere(cat);
}

  createProduct(nome: string, immagine: string, categoriaindex: string,  prezzo: number, descrizione: string, sottoCategoriaindex?: string){
    const p= this.categorie[Number(categoriaindex)]
    const prod: ProductRequest= {
      name: nome,
      image: immagine,
      category: p,
      subcategory: this.sottocategorie[p][Number(sottoCategoriaindex)],
      price: prezzo,
      description: descrizione
    }
    this.adminservice.createProduct(prod).subscribe({
      next: (res) => {
        console.log(res);
        this.showToast('Creato Correttamente')
      },
      error: (err) => {
        this.showToast('Errore');
      }
    })
  }

  deleteProd(productindex :string){

    const prodotto = this.listaprodotti[Number(productindex)];

    this.adminservice.deleteProduct(prodotto).subscribe({
      next: data => {

        if(data==null){
        console.log(data);
        this.showToast('Prodotto eliminato correttamente, AGGIORNA LA PAGINA');
        }

      },

      error: (err) => {
          console.error('Errore HTTP:', err.status, err.message);
          this.showToast('Prodotto già eliminato');
        } 
      }  
    )
  }

  ordineutenti: Order[]=[];

  OrdiniDegliUtenti(){
    this.adminservice.getAllOrder().subscribe({
      next: data =>{
        this.ordineutenti=data;
        console.log(data);
      }
    })
  }

  users: User[]=[]

  getAllUser(){
    this.adminservice.getAllUsers().subscribe({
      next: data =>{
        this.users=data;
      }
    })
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
    
    disc = false;

sortTable(column: string) {

  this.disc = !this.disc;

  if(column === 'user') {

    if(this.disc) {

      this.ordineutenti.sort(
        (a, b) => b.user.id - a.user.id
      );

    } else {

      this.ordineutenti.sort(
        (a, b) => a.user.id - b.user.id
      );

    }

  }

  if(column === 'total') {

    if(this.disc) {

      this.ordineutenti.sort(
        (a, b) => b.total - a.total
      );

    } else {

      this.ordineutenti.sort(
        (a, b) => a.total - b.total
      );

    }

  }

  if(column === 'products') {

    if(this.disc) {

      this.ordineutenti.sort(
        (a, b) =>
          b.orderItems.length - a.orderItems.length
      );

    } else {

      this.ordineutenti.sort(
        (a, b) =>
          a.orderItems.length - b.orderItems.length
      );

    }

  }

  if(column === 'date') {

    if(this.disc) {

      this.ordineutenti.sort(
        (a, b) =>
          new Date(b.time).getTime()
          - new Date(a.time).getTime()
      );

    } else {

      this.ordineutenti.sort(
        (a, b) =>
          new Date(a.time).getTime()
          - new Date(b.time).getTime()
      );

    }

  }

}
}
