package com.example.benchmark

data class Response(val name: String, val url: String)
val LOCAL_HOST_IP = "http://192.168.50.89:8000/"

val data = listOf<Response>(
    Response(
        "Lake View",
        LOCAL_HOST_IP + "Lake_View.jpeg"
    ),
    Response(
        "Brown Paint",
        LOCAL_HOST_IP + "Brown_Paint.jpeg"
    ),
    Response(
        "mountain",
        LOCAL_HOST_IP + "Mountain.jpeg"
    ),
    Response(
        "Watch and Hands",
        LOCAL_HOST_IP + "Watch_And_Hands.jpeg"
    ),
    Response(
        "Wall with Hole",
        LOCAL_HOST_IP + "Wall_With_Hole.jpeg"
    ),
    Response(
        "Forest",
        LOCAL_HOST_IP + "Forest.jpeg"
    ),
    Response(
        "Green Car",
        LOCAL_HOST_IP + "Green_Car.jpeg"
    ),
    Response(
        "Girl",
        LOCAL_HOST_IP + "Girl.jpeg"
    ),
    Response(
        "City Night View",
        LOCAL_HOST_IP + "City_Night_View.jpeg"
    ),
    Response(
        "Breakfast",
        LOCAL_HOST_IP + "Breakfast.jpeg"
    )
)

//val data = listOf<Response>(
//    Response(
//        "Lake View",
//        "https://images.unsplash.com/photo-1586227740560-8cf2732c1531?ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxfHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60"
//    ),
//    Response(
//        "Brown Paint",
//        "https://images.unsplash.com/photo-1626033170780-125ce4fad907?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyfHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60"
//    ),
//    Response(
//        "mountain",
//        "https://images.unsplash.com/photo-1626049789315-2d5f1b656454?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw4fHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60"
//    ),
//    Response(
//        "Watch and Hands",
//        "https://images.unsplash.com/photo-1625425727158-3d0af51d8a6b?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw5fHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60"
//    ),
//    Response(
//        "Wall with Hole",
//        "https://images.unsplash.com/photo-1626040840610-413f276d4ffc?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxMnx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60"
//    ),
//    Response(
//        "Forest",
//        "https://images.unsplash.com/photo-1626097363504-10b55da8b594?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxMHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60"
//    ),
//    Response(
//        "Green Car",
//        "https://images.unsplash.com/photo-1626099964608-6c30072f0812?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxNHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60"
//    ),
//    Response(
//        "Girl",
//        "https://images.unsplash.com/photo-1626089697817-424a8c0b2ea3?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxOXx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60"
//    ),
//    Response(
//        "City Night View",
//        "https://images.unsplash.com/photo-1625913952228-8d3fcc4ff5ac?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyM3x8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60"
//    ),
//    Response(
//        "Breakfast",
//        "https://images.unsplash.com/photo-1626090352063-024e8c26723a?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0NHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=60"
//    )
//)