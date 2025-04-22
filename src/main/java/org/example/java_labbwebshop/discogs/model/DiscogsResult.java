package org.example.java_labbwebshop.discogs.model;

import java.util.List;

public record DiscogsResult(int id, String title, List<String> genre) {}
