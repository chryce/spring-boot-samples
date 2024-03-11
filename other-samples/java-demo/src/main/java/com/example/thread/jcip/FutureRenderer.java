package com.example.thread.jcip;

import static com.example.thread.jcip.LaunderThrowable.launderThrowable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * FutureRenderer
 * <p/>
 * Waiting for image download with \Future
 *
 * @author Brian Goetz and Tim Peierls
 */
public abstract class FutureRenderer {

  private final ExecutorService executor = Executors.newCachedThreadPool();

  void renderPage(CharSequence source) {
    final List<ImageInfo> imageInfos = scanForImageInfo(source);
    Callable<List<ImageData>> task =
        new Callable<List<ImageData>>() {
          public List<ImageData> call() {
            List<ImageData> result = new ArrayList<ImageData>();
            for (ImageInfo imageInfo : imageInfos) {
              result.add(imageInfo.downloadImage());
            }
            return result;
          }
        };

    Future<List<ImageData>> future = executor.submit(task);
    renderText(source);

    try {
      List<ImageData> imageData = future.get();
      for (ImageData data : imageData) {
        renderImage(data);
      }
    } catch (InterruptedException e) {
      // Re-assert the thread's interrupted status
      Thread.currentThread().interrupt();
      // We don't need the result, so cancel the task too
      future.cancel(true);
    } catch (ExecutionException e) {
      throw launderThrowable(e.getCause());
    }
  }

  abstract void renderText(CharSequence s);

  abstract List<ImageInfo> scanForImageInfo(CharSequence s);

  abstract void renderImage(ImageData i);

  interface ImageData {

  }

  interface ImageInfo {

    ImageData downloadImage();
  }
}
