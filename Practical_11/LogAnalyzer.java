import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Scanner;

public class LogAnalyzer {
    public static void main(String[] args) {
        Path folder = Paths.get("logs");
        String keyword;

        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter keyword to search: ");
            keyword = sc.nextLine().trim();

            if (keyword.isEmpty()) {
                System.out.println("Keyword cannot be empty.");
                return;
            }

            Files.createDirectories(folder);

            Path log1 = folder.resolve("application.log");
            Path log2 = folder.resolve("error.log");

            if (Files.notExists(log1)) {
                Files.write(log1, List.of(
                        "Application started",
                        "INFO: User logged in",
                        "ERROR: Database connection failed",
                        "INFO: Application running",
                        "ERROR: Request timeout"));
            }

            if (Files.notExists(log2)) {
                Files.write(log2, List.of(
                        "INFO: Server started",
                        "WARNING: High memory usage",
                        "ERROR: File not found",
                        "INFO: Request completed"));
            }

            long totalLines = 0;
            long matchingLines = 0;

            try (DirectoryStream<Path> files = Files.newDirectoryStream(folder, "*.log")) {
                for (Path file : files) {
                    if (!Files.isRegularFile(file)) {
                        continue;
                    }

                    System.out.println("\nFile: " + file.getFileName());

                    BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);

                    System.out.println("File size: " + attributes.size() + " bytes");

                    List<String> lines = Files.readAllLines(file);

                    for (String line : lines) {
                        totalLines++;
                        System.out.println(line);

                        if (line.toLowerCase().contains(keyword.toLowerCase())) {
                            matchingLines++;
                        }
                    }
                }
            }

            System.out.println("\n========== SUMMARY ==========");
            System.out.println("Total lines across all files: " + totalLines);
            System.out.println("Lines containing \"" + keyword + "\": " + matchingLines);
        } catch (IOException e) {
            System.out.println("File operation failed: " + e.getMessage());
        }
    }
}