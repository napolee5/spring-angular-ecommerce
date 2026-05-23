export interface Product {
  id: number;
  name: string;
  image: string;
  category: ProdCategory;
  subcategory?: SubCategory;
  price: number;        // BigDecimal → number
  description: string;
}

export enum ProdCategory {
  ARANCE = 'ARANCE',
  MANDARINI = 'MANDARINI',
  LIMONI = 'LIMONI'
}

export enum SubCategory {
  BIONDE = 'BIONDE',
  ROSSE = 'ROSSE'
}
