package com.based64.addon.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;
import net.minecraft.command.CommandSource;
import java.util.Base64;

import meteordevelopment.meteorclient.utils.player.ChatUtils;

/**
 * The Meteor Client command API uses the <a href="https://github.com/Mojang/brigadier">same command system as Minecraft does</a>.
 */
public class ChatCommand extends Command {
    /**
     * The {@code name} parameter should be in kebab-case.
     */
    public ChatCommand() {
        super("b", "Sends a base64 message.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            error("ERROR: Missing message");
            return SINGLE_SUCCESS;
        });

        //builder.then(literal("name").then(argument("nameArgument", StringArgumentType.greedyString()).executes(context -> {
        builder.then(argument("Message", StringArgumentType.greedyString()).executes(context -> {
            String argument = StringArgumentType.getString(context, "Message");


            Base64.Encoder encoder = Base64.getEncoder();
            String encodedString = encoder.encodeToString(argument.getBytes());
            ChatUtils.sendPlayerMsg("[Begin Based64]%" + encodedString + "%");
            return SINGLE_SUCCESS;
        }));
    }
}
