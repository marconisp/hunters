export interface IFotoAvatar {
  id?: number;
  conteudoContentType?: string;
  conteudo?: string;
}

export class FotoAvatar implements IFotoAvatar {
  constructor(public id?: number, public conteudoContentType?: string, public conteudo?: string) {}
}

export function getFotoAvatarIdentifier(fotoAvatar: IFotoAvatar): number | undefined {
  return fotoAvatar.id;
}
