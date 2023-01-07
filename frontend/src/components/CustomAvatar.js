import {Avatar} from "@mui/material";

function stringToColor(string) {
    let hash = 0;
    let i;

    /* eslint-disable no-bitwise */
    for (i = 0; i < string.length; i += 1) {
        hash = string.charCodeAt(i) + ((hash << 5) - hash);
    }

    let color = '#';

    for (i = 0; i < 3; i += 1) {
        const value = (hash >> (i * 8)) & 0xff;
        color += `00${value.toString(16)}`.slice(-2);
    }
    /* eslint-enable no-bitwise */

    return color;
}

function stringAvatar(name) {
    return {
        sx: {
            bgcolor: stringToColor(name),
        },
        children: `${name.split(' ')[0][0]}${name.split(' ')[1][0]}`,
    };
}

export default function CustomAvatar ( {src, fullName, size} ) {
    if(src) return <Avatar src={src} sx={{ width:128, height:128 }}/>
    const {sx, children} = stringAvatar(fullName ? fullName : "? ?");
    sx.width = size ? size :128;
    sx.height = size ? size :128;
    return <Avatar sx={sx} children={children}/>
};