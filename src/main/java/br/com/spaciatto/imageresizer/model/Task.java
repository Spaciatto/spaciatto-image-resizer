package br.com.spaciatto.imageresizer.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    private String inputImagePath;
    private String outputImagePath;
    private Integer scaledWidth;
    private Integer scaledHeight;
    private Double scaledPercent;
}
