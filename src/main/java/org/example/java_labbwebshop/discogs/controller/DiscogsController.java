package org.example.java_labbwebshop.discogs.controller;

import lombok.AllArgsConstructor;
import org.example.java_labbwebshop.discogs.model.DiscogsResult;
import org.example.java_labbwebshop.discogs.service.DiscogsService;
import org.example.java_labbwebshop.product.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class DiscogsController {

    private final DiscogsService discogsService;

    @GetMapping("/discogs")
    public String showDiscogsResults(@RequestParam String search, Model model) {
        List<DiscogsResult> discogsResults = discogsService.searchReleases(search);
        List<Product> discogsProducts = discogsResults.stream()
                .map(result -> discogsService.saveReleaseAsProduct(result.id()))
                .toList();
        model.addAttribute("products", discogsProducts);
        return "discogsproducts";
    }


}
