import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DirectoryReport {
    private static long totalFiles = 0;
    private static long totalDirectories = 0;
    private static long totalBytes = 0;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    public static void main(String[] args) {
        Path root = Paths.get(args.length > 0 ? args[0] : "sample-folder")
                .toAbsolutePath().normalize();

        Path report = Paths.get("directory-report.txt").toAbsolutePath();

        try {
            if (!Files.isDirectory(root)) {
                Files.createDirectories(root);
                createSampleFiles(root);
            }

            Path reportParent = report.getParent();
            if (reportParent != null) {
                Files.createDirectories(reportParent);
            }

            try (java.io.BufferedWriter writer = Files.newBufferedWriter(report)) {
                writer.write("DIRECTORY TREE REPORT");
                writer.newLine();
                writer.write("Root: " + root);
                writer.newLine();
                writer.write("==============================================");
                writer.newLine();

                Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        totalDirectories++;
                        writer.write("[DIRECTORY] " + dir);
                        writer.newLine();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        totalFiles++;
                        totalBytes += attrs.size();

                        writer.write("[FILE] " + file.getFileName()
                                + " | Size: " + attrs.size() + " bytes" + " | Modified: "
                                + FORMATTER.format(attrs.lastModifiedTime().toInstant()));
                        writer.newLine();

                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                        writer.write("[ERROR] " + file + " | " + exc.getMessage());
                        writer.newLine();
                        return FileVisitResult.CONTINUE;
                    }
                });

                writer.newLine();
                writer.write("=============== SUMMARY ===============");
                writer.newLine();
                writer.write("Total directories: " + totalDirectories);
                writer.newLine();
                writer.write("Total files: " + totalFiles);
                writer.newLine();
                writer.write("Total file size: " + totalBytes + " bytes");
                writer.newLine();
            }

            System.out.println("Directory report generated successfully.");
            System.out.println("Root directory: " + root);
            System.out.println("Total directories: " + totalDirectories);
            System.out.println("Total files: " + totalFiles);
            System.out.println("Total file size: " + totalBytes + " bytes");
            System.out.println("Report saved to: " + report);
        } catch (IOException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    private static void createSampleFiles(Path root) throws IOException {
        Path subfolder = Files.createDirectories(root.resolve("documents"));
        Files.writeString(root.resolve("readme.txt"), "This is a sample file.");
        Files.writeString(subfolder.resolve("notes.txt"), "Java NIO directory traversal.");
        Files.writeString(subfolder.resolve("data.txt"), "File attributes and sizes.");
    }
}