package br.com.spaciatto.imageresizer;

import br.com.spaciatto.imageresizer.model.Task;
import br.com.spaciatto.imageresizer.service.ImageResizer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;

import java.io.File;
import java.util.List;

@CommandLine.Command(name = "ImageResizerApplication", mixinStandardHelpOptions = true, version = "Image Resizer Application V1.0", description = "Resizer images files")
public class Application implements Runnable {

    @CommandLine.Option(names = {
            "-jf",
            "--jobFile"
    },
            required = true, description = "JSON job of tasks to resizer images")
    private String jobJSONFile = "job.json";

    public static void main(String[] args) throws Exception {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            File jsonFile = new File(jobJSONFile);
            if (jsonFile.exists()) {
                ObjectMapper objectMapper = new ObjectMapper();
                System.out.println("Loading JSON tasks file...");
                List<Task> job = objectMapper.readValue(jsonFile, new TypeReference<List<Task>>() {
                });
                job.forEach(task -> {
                    System.out.println(String.format("Resizing images from: %s", task.getInputImagePath()));
                    System.out.println(String.format("                  to: %s", task.getOutputImagePath()));
                    if (task.getScaledPercent() != null && task.getScaledPercent() != 0.0) {
                        ImageResizer.resize(task.getInputImagePath(),
                                task.getOutputImagePath(), task.getScaledPercent());
                    } else {
                        ImageResizer.resize(task.getInputImagePath(),
                                task.getOutputImagePath(), task.getScaledWidth(), task.getScaledHeight());
                    }
                });
                System.out.println("Images resize complete...");
            } else {
                System.out.println("JSON tasks file not found...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
