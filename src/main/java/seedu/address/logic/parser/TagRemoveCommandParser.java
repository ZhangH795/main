package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.commands.TagRemoveCommand.TagRemoveDescriptor;
import seedu.address.logic.commands.TagRemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagRemoveCommand object
 */
public class TagRemoveCommandParser implements Parser<TagRemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagRemoveCommand
     * and returns an TagRemoveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagRemoveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String newTag;
        int lastIndex = 0;
        String[] argsArray;
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRemoveCommand.MESSAGE_USAGE));
        }
        argsArray = args.trim().split(" ");
        newTag = argsArray[0];
        for (int i = 1; i < argsArray.length; i++) {
            if (!argsArray[i].matches("\\d?")) {
                newTag = newTag.concat(" " + argsArray[i]);
                lastIndex = i;
            }
        }
        HashSet<String> tagSet = new HashSet<>();
        tagSet.add(newTag);
        ArrayList<Index> index = new ArrayList<>();

        if (lastIndex != argsArray.length) {
            try {
                for (int i = lastIndex + 1; i < argsArray.length; i++) {
                    index.add(ParserUtil.parseIndex(argsArray[i]));
                }
            } catch (IllegalValueException ive) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE));
            }
        }

        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        try {
            parseTagsForEdit(tagSet).ifPresent(tagRemoveDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!tagRemoveDescriptor.isAnyFieldEdited()) {
            throw new ParseException(TagRemoveCommand.MESSAGE_NOT_EDITED);
        }

        return new TagRemoveCommand(index, tagRemoveDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}