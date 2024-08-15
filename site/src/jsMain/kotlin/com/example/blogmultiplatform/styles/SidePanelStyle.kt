package com.example.blogmultiplatform.styles

import com.example.blogmultiplatform.models.Theme
import com.example.blogmultiplatform.util.Id
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.style.ComponentStyle

val NavigationItemStyle by ComponentStyle {
    cssRule(
        ":hover > #${Id.svgParent} > #${Id.vectorIcon}"
    ){
        Modifier.styleModifier {
            property("stroke", Theme.Primary.hex)
        }
    }

    cssRule(" > #${Id.navigationText}"){
        Modifier.color(Theme.White.rgb)
    }

    cssRule(":hover > #${Id.navigationText}"){
        Modifier.color(Theme.Primary.rgb)
    }
}