
function leftPad(value) {
    if (value >= 10) {
        return value;
    }
    return '0'+value;
}

function toStringByFormatting(source, delimiter = '-') {
    const year = source.getFullYear();
    const month = leftPad(source.getMonth() + 1);
    const day = leftPad(source.getDate());
    return [year, month, day].join(delimiter);
}

function toStringByFormattingHm(source, delimiter = '-') {
    const year = source.getFullYear();
    const month = leftPad(source.getMonth() + 1);
    const day = leftPad(source.getDate());
    const hour = leftPad(source.getHours());
    const minutes = leftPad(source.getMinutes());
    return [year, month, day].join(delimiter)+" "+[hour, minutes].join(delimiter);
}

function toStringByFormattingHms(source, delimiter = '-') {
    const year = source.getFullYear();
    const month = leftPad(source.getMonth() + 1);
    const day = leftPad(source.getDate());
    const hour = leftPad(source.getHours());
    const minutes = leftPad(source.getMinutes());
    const second = leftPad(source.getSeconds());
    return [year, month, day].join(delimiter)+" "+[hour, minutes, second].join(delimiter);
}