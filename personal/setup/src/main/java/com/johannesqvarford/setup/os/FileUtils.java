package com.johannesqvarford.setup.os;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FileUtils
{
    private FileUtils()
    {
    }

    public static void symlink(Path source, Path destination) throws IOException
    {
        boolean destinationExists = Files.exists(destination, LinkOption.NOFOLLOW_LINKS);
        boolean destinationIsNormalFileOrDirectory = destinationExists && !Files.isSymbolicLink(destination);
        boolean destinationIsSymbolicLink = destinationExists && Files.isSymbolicLink(destination);
        boolean destinationPointsCorrectly = destinationIsSymbolicLink && Files.readSymbolicLink(destination).equals(source);

        if (!destinationExists)
        {
            System.out.printf("new %s -> %s%n", destination, source);
            Files.createSymbolicLink(destination, source);
        }
        else if (destinationIsNormalFileOrDirectory || !destinationPointsCorrectly)
        {
            System.out.printf("replace %s -> %s%n", destination, source);
            Files.delete(destination);
            Files.createSymbolicLink(destination, source);
        }
    }

    public static List<Path> recursiveGetChildPaths(Path directory)
    {
        if (Files.isDirectory(directory, LinkOption.NOFOLLOW_LINKS))
        {
            return Arrays.asList(directory);
        }

        try
        {
            List<Path> list = null;
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory))
            {
                list = StreamSupport.stream(stream.spliterator(), false).collect(Collectors.toList());
            }
        
            return list.stream()
                .flatMap(p -> recursiveGetChildPaths(p).stream())
                .collect(Collectors.toList());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static List<Path> nonRecursiveGetChildPaths(Path directory)
    {
        try
        {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory))
            {
                return StreamSupport.stream(stream.spliterator(), false).collect(Collectors.toList());
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Path homeDirectory()
    {
        return Paths.get(System.getProperty("user.home"));
    }
}