package eu.macsworks.premium.macslibs.objects;

import eu.macsworks.premium.macslibs.MacsLibs;
import eu.macsworks.premium.macslibs.licensing.LicensingManager;
import eu.macsworks.premium.macslibs.utils.ColorTranslator;
import eu.macsworks.premium.macslibs.utils.LibLoader;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class MacsCommand implements CommandExecutor, TabExecutor {

    @Getter private final Plugin plugin;
    @Getter private final String commandId;
    @Getter @Setter private String requiredPerm;
    /**
     * -- SETTER --
     *  Sets whether this command accepts being ran without any arguments
     *
     * @param acceptsNoArgs
     */
    @Setter
    private boolean acceptsNoArgs = true;
    private HashMap<String, MacsSubcommand> subcommands = new HashMap<>();

    /**
     * -- SETTER --
     *  Sets the default behavior if no relevant subcommands were found. Do not set to send the usage command.
     *
     * @param consumer
     */
    @Setter
    private BiConsumer<Player, String[]> defaultBehavior;
    private BiConsumer<CommandSender, String[]> defaultBehaviorConsole;;

    @Getter @Setter private String usage;
    @Getter @Setter private String requiredArgs;

    public MacsCommand(String command, Plugin plugin){
        this.plugin = plugin;
        this.commandId = command;
        defaultBehavior = (player, args) -> player.sendMessage("§cThis command has no player behavior set.");
        defaultBehaviorConsole = (player, args) -> player.sendMessage("§cThis command has no console behavior set.");
    }

    /**
     * Adds a subcommand to this command, identified by the subcommand ID
     * @param subcommand Subcommand to add to this command
     */
    public void addSubCommand(MacsSubcommand subcommand){
        if(MacsLibs.getInstance().getScamProtected().get()){
            return;
        }

        subcommands.put(subcommand.getId().toLowerCase(), subcommand);
        subcommand.setRootCommand(this);
    }

    /**
     * Sets the default console behavior if no relevant subcommands were found. Do not set to send the usage command.
     * @param consumer
     */
    public void setDefaultConsoleBehavior(BiConsumer<CommandSender, String[]> consumer){
        this.defaultBehaviorConsole = consumer;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(MacsLibs.getInstance().getScamProtected().get()){
            commandSender.sendMessage("This command is currently blocked.\n\n§7§oMacsWorks Scam Protection §8- §amacsworks.eu");
            return true;
        }

        if(!(commandSender instanceof Player p)){
            if(strings.length == 0){
                if(acceptsNoArgs){
                    defaultBehaviorConsole.accept(commandSender, strings);
                    return true;
                }

                sendHelp(commandSender);
                return true;
            }

            if(subcommands.containsKey(strings[0].toLowerCase())){
                subcommands.get(strings[0].toLowerCase()).execute(commandSender, strings);
                return true;
            }

            if(defaultBehavior != null){
                defaultBehaviorConsole.accept(commandSender, strings);
                return true;
            }

            sendHelp(commandSender);
            return true;
        }

	    if(requiredPerm != null && !p.hasPermission(requiredPerm)){
            p.sendMessage(MacsLibs.getInstance().getLibLoader().getLang("no-permission"));
            return true;
        }

        if(strings.length == 0){
            if(acceptsNoArgs){
                defaultBehavior.accept(p, strings);
                return true;
            }

            sendHelp(p);
            return true;
        }

        if(subcommands.containsKey(strings[0].toLowerCase())){
            subcommands.get(strings[0].toLowerCase()).execute(p, strings);
            return true;
        }

        if(defaultBehavior != null){
            defaultBehavior.accept(p, strings);
            return true;
        }

        sendHelp(p);
        return true;
    }

    public void sendHelp(Player p){
        sendHelp((CommandSender) p);
    }

    public void sendHelp(CommandSender p){
        if(acceptsNoArgs){
            p.sendMessage(ColorTranslator.translate(MacsLibs.getInstance().getLibLoader().getLang("usage-subcommand")
                    .replace("%command%", commandId)
                    .replace("%subcommand%", "")
                    .replace("%args%", requiredArgs)
                    .replace("%use%", usage)));

            if(!subcommands.isEmpty()){
                subcommands.values().forEach(subcommand -> p.sendMessage(ColorTranslator.translate(MacsLibs.getInstance().getLibLoader().getLang("usage-subcommand")
                        .replace("%command%", commandId)
                        .replace("%subcommand%", subcommand.getId())
                        .replace("%args%", subcommand.getRequiredArgs())
                        .replace("%use%", subcommand.getUsage()))));
            }
            return;
        }

        p.sendMessage(ColorTranslator.translate(MacsLibs.getInstance().getLibLoader().getLang("usage-header").replace("%command%", commandId)));
        subcommands.values().forEach(subcommand -> p.sendMessage(ColorTranslator.translate(MacsLibs.getInstance().getLibLoader().getLang("usage-subcommand")
                .replace("%command%", commandId)
                .replace("%subcommand%", subcommand.getId())
                .replace("%args%", subcommand.getRequiredArgs())
                .replace("%use%", subcommand.getUsage()))));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length <= 1 && !subcommands.isEmpty()){
            if(strings.length == 0) return new ArrayList<>(subcommands.keySet());
            return subcommands.keySet().stream().filter(sub -> sub.startsWith(strings[0])).collect(Collectors.toList());
        }

        if(strings.length > 1){
            MacsSubcommand subcommand = subcommands.get(strings[0].toLowerCase());
            if(subcommand == null) return null;

            TabCompletion relevant = subcommand.getTabCompleters().get(strings.length);
            if(relevant == null) return null;
            return relevant.getCompletions();
        }
        return null;
    }
}
