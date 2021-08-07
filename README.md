# MyImageLoader

A simple image loading framework for Android that help you load images from the network, local storage and display inside ImageView.
ImageLoader also support media decoding, memory and disk caching, and resource pooling.

## Demo

Single ImageView            |  Multiple ImageView
:-------------------------:|:-------------------------:
![](https://media.giphy.com/media/IorGtKTGOUdNTV1cQF/giphy.gif)  |  ![](https://media.giphy.com/media/CGZVkYhiN4KolRMYUL/giphy.gif)


  
## How do I use ImageLoader?

Simple use cases will look something like this:
```kotlin
// Need to choose the corresponding lifecycleScope by your own
// The default supported reource type are: 
// [String] : "content", "file", "http", and "https" schemes only 
// [DrawableRes]
imageView.load(resource, lifecycleScope)
```
You can also add ```placeholder``` image and ```error``` image:
```kotlin
imageView.load(resource,, lifecycleScope) {
    placeholder(R.drawable.ic_baseline_cloud_download_24)
    error(R.drawable.ic_baseline_error_24)
}
```
You can also add ```Transformations``` on the image:
By default, ImageLoader comes packaged with 3 transformations: 
- blur
- circle crop
- grayscale
```kotlin
transformationList.add(CircleCropTransformation())
transformationList.add(GrayscaleTransformation())
transformationList.add(BlurTransformation())
imageView.load(resource, lifecycleScope) {
    transformations(transformationList)
}
```
or just one transformation.
```kotlin
imageView.load(resource, lifecycleScope) {
    transformations(CircleCropTransformation())
}
```

  
## Limitations

- Need to choose the ```lifecycleScope``` by your own 
    when calling the ```imageView.load(@resource, lifecycleScope)``` to let ImageLoader work as except.
    For more detail about ```lifecycleScope``` please refer to [google developer](https://developer.android.com/topic/libraries/architecture/coroutines).
- Only can use ```@DrawableRes``` for ```placeholder``` image and ```error``` image.
- Cannot tweak the configuration of ```cache``` and ```bitmap pool``` etc.

  
