package main.bootstrap;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import main.domain.*;
import main.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    private final CategoryRepository categoryRepository;
    private final SectionItemRepository sectionItemRepository;

    private final SectionRepository sectionRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RatingRepository ratingRepository;
    @Transactional
    @Override
    public void run(String... args) throws Exception {


        Authority admin = authorityRepository.save(Authority.builder()
                .authority("admin")
                .build());

        Authority user = authorityRepository.save(Authority.builder()
                .authority("user")
                .build());

        User boris = userRepository.save(User.builder()
                .email("boris@hotmail.com")
                .birth(LocalDate.of(1997, 11, 27))
                .password(passwordEncoder.encode("12345"))
                .firstName("Boris")
                .lastName("Dimitrijevic")
                .authority(admin)
                .build());

        User loreana = userRepository.save(User.builder()
                .email("loreana@hotmail.com")
                .birth(LocalDate.of(1992, 11, 27))
                .password(passwordEncoder.encode("12345"))
                .firstName("Loreana")
                .lastName("Beatovic")
                .authority(admin)
                .build());

        User darko = userRepository.save(User.builder()
                .email("darko@hotmail.com")
                .birth(LocalDate.of(1997, 11, 27))
                .password(passwordEncoder.encode("12345"))
                .firstName("Darko")
                .lastName("Molnar")
                .authority(user)
                .build());

        User mojica = userRepository.save(User.builder()
                .email("mojica@hotmail.com")
                .birth(LocalDate.of(1997, 11, 27))
                .password(passwordEncoder.encode("12345"))
                .firstName("Mojca")
                .lastName("Vravnik")
                .authority(user)
                .build());


        if(categoryRepository.count() == 0){


            Category men = categoryRepository.save(Category.builder()
                    .categoryName("Men")
                    .build());

            Section menClothes = sectionRepository.save(Section.builder()
                    .category(men)
                    .sectionName("Clothes")
                    .build());

            SectionItems menShirt = sectionItemRepository.save(SectionItems.builder()
                    .section(menClothes)
                    .itemName("Shirt")
                    .build());

            Product menPoloShirt = productRepository.save(Product.builder()
                    .quantity(500)
                    .sizes(getSizeSet())
                    .name("Men Polo Shirt")
                    .description("A men's polo shirt is a classic garment that combines comfort and elegance. These shirts typically feature short sleeves, a collar, and a front button placket with a few buttons. They are made from lightweight and comfortable materials such as cotton or a cotton-polyester blend.")
                    .price(19.99)
                    .brand("Paul Polo")
                    .title("Elegant Polo Shirt")
                    .discountPrecent(30)
                    .discountPrice(13.99)
                    .highlights(Arrays.asList())
                    .quantity(500)
                    .sectionItem(menShirt)
                    .colors(Arrays.asList("Black", "Orange", "Yellow", "Gray"))
                    .highlights(Arrays.asList(
                            "Comfortable Fit: Experience all-day comfort with our men's polo shirt designed for a relaxed and easy fit.",
                            "Versatile Style: From casual outings to semi-formal occasions, this polo shirt effortlessly adapts to various settings, offering a versatile wardrobe staple.",
                            "Quality Fabric: Crafted from a premium blend of cotton and polyester, the shirt ensures durability, breathability, and a soft feel against the skin.",
                            "Timeless Design: With a classic collar, short sleeves, and stylish button placket, this polo shirt boasts a timeless design that never goes out of style."
                    ))
                    .build());

            ratingRepository.save(generateRandomRating(loreana, menPoloShirt));
            ratingRepository.save(generateRandomRating(darko, menPoloShirt));
            ratingRepository.save(generateRandomRating(boris, menPoloShirt));
            ratingRepository.save(generateRandomRating(mojica, menPoloShirt));


            SectionItems menTrausers = sectionItemRepository.save(SectionItems.builder()
                    .section(menClothes)
                    .itemName("Trauses")
                    .build());
            SectionItems menJackets = sectionItemRepository.save(SectionItems.builder()
                    .section(menClothes)
                    .itemName("Jackets")
                    .build());


            Section menShoes = sectionRepository.save(Section.builder()
                    .category(men)
                    .sectionName("Shoes")
                    .build());


            SectionItems menSportShoes = sectionItemRepository.save(SectionItems.builder()
                    .section(menShoes)
                    .itemName("Sport")
                    .build());
            SectionItems menRunningShoes = sectionItemRepository.save(SectionItems.builder()
                    .section(menShoes)
                    .itemName("Running")
                    .build());
            SectionItems menSlippers = sectionItemRepository.save(SectionItems.builder()
                    .section(menShoes)
                    .itemName("Slippers")
                    .build());


            Section menAccesories = sectionRepository.save(Section.builder()
                    .category(men)
                    .sectionName("Accesories")
                    .build());

            sectionItemRepository.save(SectionItems.builder()
                    .section(menAccesories)
                    .itemName("Bags")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(menAccesories)
                    .itemName("Belts")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(menAccesories)
                    .itemName("Hats")
                    .build());

            //            ----------------------------------------------------------


            Category kids = categoryRepository.save(Category.builder()
                    .categoryName("Kids")
                    .build());

            Section kidClothes = sectionRepository.save(Section.builder()
                    .category(kids)
                    .sectionName("Clothes")
                    .build());


            sectionItemRepository.save(SectionItems.builder()
                    .section(kidClothes)
                    .itemName("Shirt")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(kidClothes)
                    .itemName("Trauses")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(kidClothes)
                    .itemName("Jackets")
                    .build());


            Section kidShoes = sectionRepository.save(Section.builder()
                    .category(kids)
                    .sectionName("Shoes")
                    .build());

            sectionItemRepository.save(SectionItems.builder()
                    .section(kidShoes)
                    .itemName("Sport")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(kidShoes)
                    .itemName("Running")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(kidShoes)
                    .itemName("Slippers")
                    .build());

            Section kidAccesorie = sectionRepository.save(Section.builder()
                    .category(kids)
                    .sectionName("Accesories")
                    .build());

            sectionItemRepository.save(SectionItems.builder()
                    .section(kidAccesorie)
                    .itemName("Bags")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(kidAccesorie)
                    .itemName("Belts")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(kidAccesorie)
                    .itemName("Hats")
                    .build());


            //            ----------------------------------------------------------


            Category women = categoryRepository.save(Category.builder()
                    .categoryName("Women")
                    .build());

            Section womenClothes = sectionRepository.save(Section.builder()
                    .category(women)
                    .sectionName("Clothes")
                    .build());


            sectionItemRepository.save(SectionItems.builder()
                    .section(womenClothes)
                    .itemName("Shirt")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(womenClothes)
                    .itemName("Trauses")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(womenClothes)
                    .itemName("Jackets")
                    .build());


            Section womenShoes = sectionRepository.save(Section.builder()
                    .category(women)
                    .sectionName("Shoes")
                    .build());

            sectionItemRepository.save(SectionItems.builder()
                    .section(womenShoes)
                    .itemName("Sport")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(womenShoes)
                    .itemName("Running")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(womenShoes)
                    .itemName("Slippers")
                    .build());


            Section womenAccesories = sectionRepository.save(Section.builder()
                    .category(women)
                    .sectionName("Accesories")
                    .build());

            sectionItemRepository.save(SectionItems.builder()
                    .section(womenAccesories)
                    .itemName("Bags")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(womenAccesories)
                    .itemName("Belts")
                    .build());
            sectionItemRepository.save(SectionItems.builder()
                    .section(womenAccesories)
                    .itemName("Hats")
                    .build());

        }



    }

    private Rating  generateRandomRating(User user, Product product){

        Random random = new Random();

        return Rating.builder()
                .rating(random.nextDouble(10))
                .user(user)
                .product(product)
                .build();
    }

    private Set<Size> getSizeSet(){
        Set<Size> sizes = new HashSet<>();
        sizes.add(Size.builder()
                        .size("S")
                        .quantity(100)
                        .build());
                sizes.add(Size.builder()
                        .size("M")
                        .quantity(100)
                        .build());
                sizes.add(Size.builder()
                        .size("L")
                        .quantity(100)
                        .build());
                sizes.add(Size.builder()
                        .size("XL")
                        .quantity(100)
                        .build());
                sizes.add(Size.builder()
                        .size("XXL")
                        .quantity(100)
                        .build());

                return  sizes;
    }
}
