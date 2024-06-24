package eu.macsworks.premium.macslibs.objects;

import eu.macsworks.premium.macslibs.MacsLibs;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public class MacsSubcommand {

    @Getter private final String id;
    @Getter private String usage;
    @Getter private String requiredArgs;
    @Getter @Setter private String requiredPerm;

    @Getter @Setter private MacsCommand rootCommand;

    @Setter private BiConsumer<Player, String[]> commandInfo;

    @Setter private BiConsumer<CommandSender, String[]> consoleCommandInfo;

    public MacsSubcommand(String id){
        this.id = id;
    }

    public void setUsage(String usage){
        this.usage = usage;
    }

    public void setRequiredArgs(String requiredArgs){
        this.requiredArgs = " " + requiredArgs;
    }

    public void execute(Player sender, String[] args) {
        if(requiredPerm != null && !sender.hasPermission(requiredPerm)){
            sender.sendMessage(MacsLibs.getInstance().getLibLoader().getLang("no-permission"));
            return;
        }

        int requiredArgsAmt = 0;
        for(int i = 0; i < requiredArgs.length(); i++) if(requiredArgs.charAt(i) == '<') requiredArgsAmt++;

        if(args.length < requiredArgsAmt + 1){
            getRootCommand().sendHelp(sender);
            return;
        }

        commandInfo.accept(sender, args);
    }

    public void execute(CommandSender sender, String[] args) {
        int requiredArgsAmt = 0;
        for(int i = 0; i < requiredArgs.length(); i++) if(requiredArgs.charAt(i) == '<') requiredArgsAmt++;

        if(args.length < requiredArgsAmt + 1){
            getRootCommand().sendHelp(sender);
            return;
        }

        consoleCommandInfo.accept(sender, args);
    }

}
