import { ProdCategory, SubCategory } from "./product.model";

export interface ProductRequest {
  name: string;
  image: string;
  category: ProdCategory;
  subcategory?: SubCategory;
  price: number;        // BigDecimal → number
  description: string;
}