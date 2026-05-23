package project.config;

import project.entities.Cart;
import project.entities.Product;
import project.entities.User;
import project.enums.ProdCategory;
import project.enums.Role;
import project.enums.SubCategory;
import project.repository.CartRepository;
import project.repository.ProductRepository;
import project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedDatabase() {
        return args -> {

            /* =======================
               UTENTI + CARRELLI
               ======================= */
            if (userRepository.count() == 0) {

                Cart userCart = Cart.builder()
                        .items(new ArrayList<>())
                        .grandTotal(BigDecimal.ZERO)
                        .build();

                User user = User.builder()
                        .name("Mario")
                        .surname("Rossi")
                        .email("mario.rossi@example.com")
                        .password(passwordEncoder.encode("password123"))
                        .role(Role.USER)
                        .cart(userCart)
                        .build();

                userCart.setUser(user);
                userRepository.save(user);

                Cart adminCart = Cart.builder()
                        .items(new ArrayList<>())
                        .grandTotal(BigDecimal.ZERO)
                        .build();

                User admin = User.builder()
                        .name("Admin")
                        .surname("Admin")
                        .email("nap9chris@gmail.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .cart(adminCart)
                        .build();

                adminCart.setUser(admin);
                userRepository.save(admin);

                System.out.println("Utenti demo creati (USER / ADMIN)");
            }

            /* =======================
               PRODOTTI
               Prezzi REALI al KG
               ======================= */
            if (productRepository.count() == 0) {

                List<Product> products = List.of(

                        /* ===== ARANCE ===== */
                        Product.builder()
                                .name("Arancia Navel")
                                .image("assets/Navel.jpg")
                                .category(ProdCategory.ARANCE)
                                .subcategory(SubCategory.BIONDE)
                                .price(BigDecimal.valueOf(2.99))
                                .description("Dolci e succose, perfette da spremere.")
                                .build(),
                        Product.builder()
                                .name("Arancia Tarocco")
                                .image("assets/Tarocco.jpg")
                                .category(ProdCategory.ARANCE)
                                .subcategory(SubCategory.ROSSE)
                                .price(BigDecimal.valueOf(3.49))
                                .description("Ideali per spremute ricche di gusto.")
                                .build(),
                        Product.builder()
                                .name("Arancia Sanguinello")
                                .image("assets/Sanguinello.jpg")
                                .category(ProdCategory.ARANCE)
                                .subcategory(SubCategory.ROSSE)
                                .price(BigDecimal.valueOf(3.79))
                                .description("Dal gusto intenso, tipiche siciliane.")
                                .build(),

                        Product.builder()
                                .name("Arancia Washington")
                                .image("assets/Washington.jpg")
                                .category(ProdCategory.ARANCE)
                                .subcategory(SubCategory.BIONDE)
                                .price(BigDecimal.valueOf(2.89))
                                .description("Polpa tenera, senza semi.")
                                .build(),
                        Product.builder()
                                .name("Arancia Moro")
                                .image("assets/Moro.jpg")
                                .category(ProdCategory.ARANCE)
                                .subcategory(SubCategory.ROSSE)
                                .price(BigDecimal.valueOf(3.99))
                                .description("Colore scuro e gusto deciso.")
                                .build(),
                        Product.builder()
                                .name("Arancia Valencia")
                                .image("assets/Valencia.jpg")
                                .category(ProdCategory.ARANCE)
                                .subcategory(SubCategory.BIONDE)
                                .price(BigDecimal.valueOf(2.69))
                                .description("Perfette da mangiare o spremere.")
                                .build(),

                        /* ===== LIMONI ===== */
                        Product.builder()
                                .name("Limone Femminello Siracusano")
                                .image("assets/LimoneFemminello.jpg")
                                .category(ProdCategory.LIMONI)
                                .subcategory(null)
                                .price(BigDecimal.valueOf(2.60)) // €/KG
                                .description("Molto succoso, profumatissimo.")
                                .build(),

                        Product.builder()
                                .name("Limone Verdello")
                                .image("assets/LimoneVerdello.jpg")
                                .category(ProdCategory.LIMONI)
                                .subcategory(null)
                                .price(BigDecimal.valueOf(3.60)) // €/KG
                                .description("Noto per il suo colore verde intenso e la buccia sottile e profumata. ")
                                .build(),

                        /* ===== MANDARINI ===== */
                        Product.builder()
                                .name("Mandarino Tardivo di Ciaculli")
                                .image("assets/MandarinoCiaculli.jpeg")
                                .category(ProdCategory.MANDARINI)
                                .subcategory(null)
                                .price(BigDecimal.valueOf(3.20)) // €/KG
                                .description("Dolce e aromatico, presidio Slow Food.")
                                .build(),

                        Product.builder()
                                .name("Mandarino Clementino")
                                .image("assets/MandariniClementini.jpg")
                                .category(ProdCategory.MANDARINI)
                                .subcategory(null)
                                .price(BigDecimal.valueOf(2.70)) // €/KG
                                .description("Senza semi, molto apprezzato.")
                                .build()
                );

                productRepository.saveAll(products);
                System.out.println("Prodotti demo inseriti nel database.");
            }
        };
    }
}
