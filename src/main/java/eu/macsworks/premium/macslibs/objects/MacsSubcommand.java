package eu.macsworks.premium.macslibs.objects;

import eu.macsworks.premium.macslibs.MacsLibs;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class MacsSubcommand {

    @Getter private final String id;
    @Setter
    @Getter private String usage;
    @Getter private String requiredArgs;
    @Getter @Setter private String requiredPerm;

    @Getter @Setter private MacsCommand rootCommand;

    @Setter private BiConsumer<Player, String[]> commandInfo;
    @Getter private Map<Integer, TabCompletion> tabCompleters = new HashMap<>();

    @Setter private BiConsumer<CommandSender, String[]> consoleCommandInfo;

    public MacsSubcommand(String id){
        this.id = id;
    }

	public void addTabCompletion(int argsAmt, List<String> totalStrings, Predicate<String> filter) { tabCompleters.put(argsAmt, new TabCompletion(argsAmt, totalStrings, filter)); }
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
