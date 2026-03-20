package com.based64.addon.mixin;

import meteordevelopment.meteorclient.mixininterface.IChatHud;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import net.minecraft.util.collection.ArrayListDeque;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Base64;

import static org.apache.commons.lang3.StringUtils.split;

/**
 * Example Mixin class.
 * For more resources, visit:
 * <ul>
 * <li><a href="https://fabricmc.net/wiki/tutorial:mixin_introduction">The FabricMC wiki</a></li>
 * <li><a href="https://github.com/SpongePowered/Mixin/wiki">The Mixin wiki</a></li>
 * <li><a href="https://github.com/LlamaLad7/MixinExtras/wiki">The MixinExtras wiki</a></li>
 * <li><a href="https://jenkins.liteloader.com/view/Other/job/Mixin/javadoc/allclasses-noframe.html">The Mixin javadoc</a></li>
 * <li><a href="https://github.com/2xsaiko/mixin-cheatsheet">The Mixin cheatsheet</a></li>
 * </ul>
 */
@Mixin(ChatHud.class)
public abstract class AutoDecrypt implements IChatHud {

    @Shadow
    public abstract ArrayListDeque<String> getMessageHistory();




    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("TAIL"))
    public void InjectAddMessage(Text message, MessageSignatureData signatureData, MessageIndicator indicator, CallbackInfo ci) {
        String messageString = message.toString();
        if (messageString.contains("[Begin Based64]%")) {
            String[] splitString = split(messageString, "%");
            byte[] Bytes = Base64.getDecoder().decode(splitString[1]);
            String byteString = new String(Bytes);
            message = Text.literal(byteString);
            meteor$add(message, 0);


        }


    }

}


