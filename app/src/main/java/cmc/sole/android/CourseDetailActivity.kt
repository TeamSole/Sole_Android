package cmc.sole.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.databinding.ActivityCourseDetailBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.overlay.PolylineOverlay


class CourseDetailActivity: AppCompatActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityCourseDetailBinding

    // NaverMap
    lateinit var naverMap: NaverMap

    // NaverMap Course
    var path = PathOverlay()
    var polyline  = PolylineOverlay()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)

        var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as MapFragment?
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.map, mapFragment).commit()
        }

        mapFragment!!.getMapAsync(this)

        setContentView(binding.root)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        // MEMO: Polyline 넣기
        // UPDATE: 각 코스 선으로 이어주기!
        polyline.coords = listOf(
            LatLng(37.57152, 126.97714),
            LatLng(37.56607, 126.98268),
            LatLng(37.56445, 126.97707),
            LatLng(37.55855, 126.97822)
        )
        polyline.setPattern(10, 5)
        polyline.map = naverMap

        // MEMO: CameraPosition 변경
        // UPDATE: 각 코스마다 특정 CameraPosition 입력해주기
        /*
        val cameraPosition = CameraPosition(
            LatLng(37.5666102, 126.9783881), // 대상 지점
            16.0, // 줌 레벨
            20.0, // 기울임 각도
            180.0 // 베어링 각도
        )
        naverMap.cameraPosition = cameraPosition
        */
        // 위치(위도,경도) 객체
        val location = LatLng(37.5666102, 126.9783881)
        // 카메라 위치와 줌 조절(숫자가 클수록 확대)
        val cameraPosition = CameraPosition(location, 16.0)
        naverMap.cameraPosition = cameraPosition

        // MEMO: Marker 모양 변경
        // UPDATE: 특정 위치 좌표를 통해 숫자 입력해주기
        /*
        val marker = Marker()
        marker.icon = OverlayImage.fromResource(R.drawable.ic_radio_check)
        marker.position = LatLng(37.5670135, 126.9783740)
        marker.map = naverMap
        */
    }
}