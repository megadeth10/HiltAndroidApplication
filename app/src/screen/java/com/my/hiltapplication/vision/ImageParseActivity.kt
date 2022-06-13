package com.my.hiltapplication.vision

import android.view.View
import com.my.hiltapplication.base.BaseActivity
import com.my.hiltapplication.databinding.ActivityImageParseBinding

class ImageParseActivity : BaseActivity<ActivityImageParseBinding>(),
    View.OnClickListener {
    //    // Demo
//    private val cloudVisionApi: CloudVisionApi by inject()
//    private var sig: String = ""
//
//    private val disposable = CompositeDisposable()
//    private var cloudVisionDisposable: Disposable? = null
//    private var resultFileName: String = ""
//    private var fos: FileWriter? = null
//    private var pw: BufferedWriter? = null
//    private lateinit var fileWritePublishSubject: PublishSubject<String>
//    private val responseStringBuilder = StringBuffer()
//    private val visionKey = Name().a()
//    private val startId = 2500
//    private val endId = 3116
//
//    private var visionAnalyticItem: ArrayList<VisionAnalyticItem>? = null
//    private var visionMappingTable: HashMap<String, VisionMapItem>? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        this.TAG = ImageParseActivity::class.simpleName
//        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
//        this.resultFileName =
//            "${storageDir.absolutePath}/imageParsingResult_0422_${this.startId}-${this.endId}.json"
//        Log.e(TAG, "Filepath: ${this.resultFileName}")
//        this.createFileObservable()
//        this.parseVisionMapData()
//        this.parseImageVisionAnalyticMapData()
//    }
//
//    override fun getLayoutId() =
//
//    override fun setContentBinding() {
//        this.sig = PackageManagerUtils.getSignature(this)
//        this.contentBinding.tvResult.text = ""
//        this.contentBinding.btnStart.setOnClickListener(this)
//    }
//
//    /**
//     * 구글 비전 분석을 위한 정보
//     */
//    private fun parseImageVisionAnalyticMapData() {
//        if (this.visionAnalyticItem == null) {
//            SingleSubject.just(true)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
//                .subscribe { t1, t2 ->
//                    var inputStream: InputStream? = null
//                    try {
//                        inputStream =
//                            this.assets.open("vision_parse_items.json")
//                        val bufferReader = BufferedReader(InputStreamReader(inputStream))
//                        val sb = StringBuilder()
//                        while (true) {
//                            val str = bufferReader.readLine() ?: break
//                            sb.append(str)
//                        }
//                        inputStream.close()
//                        val gson = GsonBuilder().setLenient().create()
//                        val jsonData = gson.fromJson(sb.toString(), VisionAnalyticJson::class.java)
//                        this.makeVisionAnalyticItem(jsonData)
//                    } catch (e: java.lang.Exception) {
//                        inputStream?.let {
//                            it.close()
//                        }
//                        Log.e(TAG, "parseVisionMapData()", e)
//                    }
//                }
//        }
//    }
//
//    private fun makeVisionAnalyticItem(jsonData: VisionAnalyticJson) {
//        if (this.visionAnalyticItem == null) {
//            this.visionAnalyticItem = arrayListOf()
//            this.visionAnalyticItem?.let { arrayList ->
//                arrayList.clear()
//                jsonData.data.forEach {
//                    arrayList.add(it)
//                }
//                this.setButtonState()
//            }
//        }
//        this.setText("image 분석 목록 load 완료")
//    }
//
//    private fun parseVisionMapData() {
//        if (this.visionMappingTable == null) {
//            SingleSubject.just(true)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
//                .subscribe { t1, t2 ->
//                    var inputStream: InputStream? = null
//                    try {
//                        inputStream =
//                            this.assets.open("vision_mapping_data.json")
//                        val bufferReader = BufferedReader(InputStreamReader(inputStream))
//                        val sb = StringBuilder()
//                        while (true) {
//                            val str = bufferReader.readLine() ?: break
//                            sb.append(str)
//                        }
//                        inputStream.close()
//                        val gson = GsonBuilder().setLenient().create()
//                        val jsonData = gson.fromJson(sb.toString(), VisionJsonRootItem::class.java)
//                        this.makeVisionMapTable(jsonData)
//                    } catch (e: Exception) {
//                        inputStream?.let {
//                            it.close()
//                        }
//                        Log.e(TAG, "parseVisionMapData()", e)
//                    }
//                }
//        }
//    }
//
//    private fun makeVisionMapTable(jsonData: VisionJsonRootItem) {
//        if (this.visionMappingTable == null) {
//            this.visionMappingTable = hashMapOf()
//            this.visionMappingTable?.let { hashMap ->
//                hashMap.clear()
//                jsonData.data.forEach {
//                    hashMap[it.objectname] = VisionMapItem(it)
//                }
//                this.setButtonState()
//            }
//        }
//        this.setText("vision 맵핑 테이블 load 완료")
//    }
//
//    private fun reStoreFile() {
//        val file = File(this.resultFileName)
//        if (file.exists()) {
//            file.delete()
//        }
//
////        this.resultFile = File.createTempFile(
////            "imageParsingResult", /* prefix */
////            ".json", /* suffix */
////            storageDir /* directory */
////        )
//    }
//
//    private fun createFileObservable() {
//        this.fileWritePublishSubject = PublishSubject.create<String>()
//        this.fileWritePublishSubject.subscribeOn(Schedulers.newThread())
//            .observeOn(Schedulers.io())
//            .subscribe(object : Observer<String> {
//                override fun onComplete() {
//                }
//
//                override fun onSubscribe(d: Disposable?) {
//                    disposable.add(d)
//                }
//
//                override fun onNext(t: String?) {
//                    t?.let {
//                        fileWrite(t)
//                    }
//                }
//
//                override fun onError(e: Throwable?) {
//                }
//            })
//    }
//
//    private fun setText(text: String) {
//        this.contentBinding.tvResult.post {
//            this.contentBinding.tvResult.append(text + "\n")
//        }
//    }
//
//    private fun setButtonState(newState: Boolean? = null) {
//        this.contentBinding.btnStart.post {
//            var enable = false
//            if(newState != null) {
//                enable = newState
//            } else {
//                var parseItemSize = 0
//                this.visionAnalyticItem?.let{
//                    parseItemSize = it.size
//                }
//                var mappingItemSize = 0
//                this.visionMappingTable?.let{
//                    mappingItemSize = it.size
//                }
//                enable = parseItemSize > 0 && mappingItemSize > 0
//            }
//            this.contentBinding.btnStart.isEnabled = enable
//        }
//    }
//
//    private fun fileWrite(response: String) {
//        try {
//            fos = FileWriter(File(this.resultFileName), true)
//            pw = BufferedWriter(fos)
//            pw?.write(response)
//            pw?.flush()
//            pw?.close()
//            fos?.close()
//        } catch (e: FileNotFoundException) {
//            this.setButtonState(true)
//            e.printStackTrace()
//        } catch (e: IOException) {
//            this.setButtonState(true)
//            e.printStackTrace()
//        }
//    }
//
//    private fun startImageParse() {
//        this.setButtonState(false)
//        val list = this.visionAnalyticItem
//        list?.let{
//            for (i in this.startId until this.endId) { // list.size){
//                val item = it[i]
//                this.requestInit(
//                    item.imageid,
//                    "======================== number: ${item.Itemid} / ${item.imageid} image file result ========================\n"
//                )
//            }
//        }
//    }
//
//    fun getBitmapFromAsset(strName: String?): Bitmap {
//        val assetManager = assets
//        var istr: InputStream? = null
//        try {
//            istr = assetManager.open(strName!!)
//        } catch (e: IOException) {
//            e.printStackTrace()
//            throw e
//        } catch (e: Exception) {
//            throw e
//        }
//        return BitmapFactory.decodeStream(istr)
//    }
//
//    private fun requestInit(fileName: String, title: String) {
//        try {
//            // scale the image to save on bandwidth
//            val uri = fileName
//            val bitmap = ImageUtil.scaleBitmapDown(
//                this.getBitmapFromAsset(uri),
//                ImageUtil.MAX_DIMENSION
//            )
//            setText("$fileName 시작")
//            this.requestVision(
//                bitmap,
//                title
//            )
//            bitmap.recycle()
//        } catch (e: IOException) {
//            setText("Image picking failed because " + e.message)
//            this.setButtonState(true)
//        } catch (e: Exception) {
//            setText("Image picking failed because " + e.message)
//        }
//    }
//
//    private fun requestVision(bitmap: Bitmap, title: String) {
//        if (cloudVisionApi == null) {
//            setText("cloudVisionApi is null")
//            return
//        }
//        val mediaType: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()
//
//        val feature = Feature().apply {
//            type = "IMAGE_PROPERTIES"
//            maxResults = 1
//        }
//
//        val feature2 = Feature().apply {
//            type = "LABEL_DETECTION"
//            maxResults = 1
//        }
//        val feature3 = Feature().apply {
//            type = "LOGO_DETECTION"
//            maxResults = 1
//        }
//        val feature4 = Feature().apply {
//            type = "OBJECT_LOCALIZATION"
//            maxResults = 1
//        }
//        val feature5 = Feature().apply {
//            type = "TEXT_DETECTION"
//            maxResults = 1
//        }
//
//
//        val features = ArrayList<Feature>().apply {
////            add(feature)
//            add(feature2)
////            add(feature3)
//            add(feature4)
////            add(feature5)
//        }
//        val base64EncodedImage =
//            Image()
//        // Convert the bitmap to a JPEG
//        // Just in case it's a format that Android understands but Cloud Vision
//        val byteArrayOutputStream =
//            ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)
//        val imageBytes = byteArrayOutputStream.toByteArray()
//        base64EncodedImage.encodeContent(imageBytes)
//
//        val objectLocalization = ObjectLocalization()
//        objectLocalization.image.content = base64EncodedImage.content
//        objectLocalization.features = features
//
//        val objectLocalizationRequestJson = ObjectLocalizationRequestJson().apply {
//            requests.add(objectLocalization)
//        }
//        val gSon = Gson()
//        val body = RequestBody.create(mediaType, gSon.toJson(objectLocalizationRequestJson))
//        val observable = cloudVisionApi!!.postCloudVision(
//            sig = this.sig,
//            packageName = this.packageName,
//            body = body,
//            authorization = visionKey
//        ).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//        this.cloudVisionDisposable =
//            observable.subscribeWith(object : DisposableSingleObserver<VisionAnnotations>() {
//                override fun onSuccess(t: VisionAnnotations?) {
//                    setButtonState(true)
//                    removeDisposable(cloudVisionDisposable)
//                    t?.let {
//                        if (t.responses.size > 0) {
//                            setText(t.toString())
//                            fileWritePublishSubject.onNext(title + t.toString())
//                            return
//                        }
//                    }
//                    setText("Image analytic failed because not have response")
//                }
//
//                override fun onError(e: Throwable?) {
//                    setButtonState(true)
//                    removeDisposable(cloudVisionDisposable)
//                    setText("Image analytic failed because " + e?.message)
//                }
//            })
//        this.disposable.add(this.cloudVisionDisposable)
//    }
//
//    override fun onBackPressed() {
//        if (this.contentBinding.btnStart.isEnabled) {
//            super.onBackPressed()
//        }
//    }
//
//    override fun getContentLayoutId() = R.layout.activity_image_parse
//
//    override fun getLogName() = ImageParseActivity::class.simpleName
//
//    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.btn_start -> {
//                this.reStoreFile()
//                this.setText("시작")
//                this.startImageParse()
//            }
//        }
//    }
//
//    /**
//     * 처리가 완료된 disposable을 명시적으로 삭제
//     */
//    private fun removeDisposable(disposable: Disposable?) {
//        disposable?.let {
//            this.disposable.delete(disposable)
//        }
//    }
//
//    override fun onDestroy() {
//        this.disposable.dispose()
//        super.onDestroy()
//    }
    override fun getContentLayoutId() : Int {
        TODO("Not yet implemented")
    }

    override fun getLogName() : String? {
        TODO("Not yet implemented")
    }

    override fun onClick(p0 : View?) {
        TODO("Not yet implemented")
    }
}
